package converter;

import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import jsontopojo.CompanyProperties;
import model.Company;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResultToCompanyConverter implements Converter<CompanyProperties, Company> {
  
  @Override
  public Company convert(CompanyProperties source) {
    Company company =
        new Company(
            source.getCreatedate(),
            source.getDomain(),
            source.getHs_lastmodifieddate(),
            source.getHs_object_id(),
            source.getName());

    return company;
  }

  public List<Company> convertAll(List<CompanyProperties> sources) {
    return sources.stream().map(this::convert).collect(Collectors.toList());
  }
}
