package model;

import java.math.BigInteger;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Company {

    private Date createdate;
    private String domain;
    private Date hs_lastmodifieddate;
    private BigInteger hs_object_id;
    private String name;

    public Company(){
    }
}