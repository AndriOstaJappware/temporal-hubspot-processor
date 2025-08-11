package activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import jsontopojo.Companies;
import jsontopojo.CompanyProperties;
import jsontopojo.CompaniesResult;
import model.Company;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import converter.ResultToCompanyConverter;
import service.KafkaRestHubSpotProducerService;

@Slf4j
public class HubspotActivitiesImpl implements HubSpotActivities {

  private final RestTemplate restTemplate;
  private final String apiUrl;
  private final int limit;
  private boolean exit = false;
  private long after = 0L;
  private final ResultToCompanyConverter resultToCompanyConverter;
  private final KafkaRestHubSpotProducerService kafkaRestHubSpotProducerService;

  public HubspotActivitiesImpl(String apiUrl, int limit) {
    this.apiUrl = apiUrl;
    this.limit = limit;
    this.restTemplate = new RestTemplate();
    this.resultToCompanyConverter = new ResultToCompanyConverter();
    this.kafkaRestHubSpotProducerService = new KafkaRestHubSpotProducerService();
  }

  @Override
  public List<CompanyProperties> read(String apikey) {
    log.info("***************************************************************************");
    log.info("Fetching companies from URL: {} with limit: {} and pagination after: {}", apiUrl, limit, after);
    log.info("***************************************************************************");

    List<CompanyProperties> companyProperties = new ArrayList<>();

    String url = String.format("%s?limit=%d&archived=false&after=%d&hapikey=%s", apiUrl, limit, after, apikey);

    ResponseEntity<Companies> response = restTemplate.getForEntity(url, Companies.class);

    Companies body = response.getBody();

    if (body == null || body.getComapnyResults() == null) {
      log.warn("Received null or empty response body.");
      this.exit = true;
      return companyProperties;
    }

    for (CompaniesResult result : body.getComapnyResults()) {
      if (result != null && result.getCompanyProperties() != null) {
        companyProperties.add(result.getCompanyProperties());
      }
    }

    if (body.getPaging() == null || body.getPaging().getNext() == null) {
      log.info("No more pages available - finishing data fetch.");
      this.exit = true;
    } else {
      this.after = body.getPaging().getNext().getAfter() != null ? body.getPaging().getNext().getAfter() : 0L;
    }

    return companyProperties;
  }

  @Override
  public boolean isExit() {
    return exit;
  }

  @Override
  public List<Company> process(List<CompanyProperties> companyProperties) {
    return resultToCompanyConverter.convertAll(companyProperties);
  }

  @Override
  public void write(List<Company> companies) {
    companies.forEach(kafkaRestHubSpotProducerService::publishData);
  }
}
