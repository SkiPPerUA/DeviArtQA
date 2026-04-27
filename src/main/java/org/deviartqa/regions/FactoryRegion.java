package org.deviartqa.regions;

import org.deviartqa.TestScenario;

public class FactoryRegion {

    Region region;

    public Region setRegion(String country){
        System.out.println(TestScenario.region);

        if (country == null){
            setRegion(TestScenario.region);
        }else {

            if (country.equals("IT")) {
                region = new Italy();
            } else if (country.equals("RO")) {
                region = new Romania();
            }

        }

        TestScenario.region = region.code;

        return region;
    }
}
