package activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jsontopojo.Companies;
import jsontopojo.CompanyProperties;
import lombok.extern.slf4j.Slf4j;
import model.Company;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import converter.ResultToCompanyConverter;
import service.KafkaRestHubSpotProducerService;

@Slf4j
public class HubspotActivitiesImpl implements HubSpotActivities {

  private final RestTemplate restTemplate = new RestTemplate();
  private final String apiUrl;
  private int limit;
  private boolean exit = false;
  private long after;
  private ResultToCompanyConverter resultToCompanyConverter = new ResultToCompanyConverter();
  private KafkaRestHubSpotProducerService kafkaRestHubSpotProducerService = new KafkaRestHubSpotProducerService();



  public HubspotActivitiesImpl( String apiUrl, int limit) {
    this.apiUrl = apiUrl;
    this.limit = limit;
  }

  @Override
  public List<CompanyProperties> read(String apikey) {
    log.info("****************************************************************************");
    log.info("URL: {}. Limit No: {}. Current Page: {}.", apiUrl, limit, after);
    log.info("****************************************************************************");
    List<CompanyProperties> companyProperties = new ArrayList<>();
    ResponseEntity<Companies> response = restTemplate.getForEntity(apiUrl + "?limit=" +
                                                                       limit + "&archived=false" + "&after=" + after + "&hapikey=" + apikey, Companies.class);
    response.getBody().getComapnyResults().forEach(dealsResult -> companyProperties.add(dealsResult.getCompanyProperties()));
    if(Objects.isNull(response.getBody().getPaging())) {
      log.info("Step is finished - CONGRATS,NO MORE ITEMS");
      this.exit = true;
    }
    after = Objects.requireNonNull(response.getBody()).getPaging().getNext().getAfter();

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
    companies.forEach(item -> kafkaRestHubSpotProducerService.publishData(item));
  }
}
