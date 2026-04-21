package org.deviartqa.regions;

public class FactoryRegion {

    Region region;

    public Region setRegion(Country country){
        if (country == Country.Italy){
            region = new Italy();
        }else if (country == Country.Romania){
            region = new Romania();
        }

        return region;
    }
}
