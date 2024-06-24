package org.deviartqa.blocks.filters;

import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class FilterStreams extends Filter{
    public FilterStreams(){
        typePage = "Stream";
    }
    public FilterStreams setArchived(String data){
        super.setArchived(data);
        return this;
    }
    public FilterStreams setProduct(String data){
        super.setProduct(data);
        return this;
    }
    public FilterStreams setTraffic_source(String data){
        super.setTraffic_source(data);
        return this;
    }
    public FilterStreams setCode(String data){
        super.setCode(data);
        return this;
    }
    public FilterStreams setName(String data){
        super.setName(data);
        return this;
    }
    public FilterStreams setLanding_page(String data){
        super.setLanding_page(data);
        return this;
    }
    public FilterStreams setPrelanding_page(String data){
        super.setPrelanding_page(data);
        return this;
    }
}
