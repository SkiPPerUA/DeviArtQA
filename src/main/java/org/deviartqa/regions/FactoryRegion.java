package org.deviartqa.regions;

public class FactoryRegion {

    Region region;

    public Region setRegion(String country){
        if (country.equals("IT")){
            region = new Italy();
        }else if (country.equals("RO")){
            region = new Romania();
        }

        return region;
    }
}
