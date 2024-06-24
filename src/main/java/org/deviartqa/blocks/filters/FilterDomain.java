package org.deviartqa.blocks.filters;

public class FilterDomain extends Filter{
    public FilterDomain(){
        typePage = "SysDomain";
    }
    public FilterDomain setTime_added(String data){
        super.setTime_added(data);
        return this;
    }
    public FilterDomain setStatus(String data){
        super.setStatus(data);
        return this;
    }
    public FilterDomain setDomainType(String data){
        super.setType(data);
        return this;
    }
    public FilterDomain setDomain(String data){
        super.setDomain(data);
        return this;
    }

    public FilterDomain setNotes(String data){
        super.setNotes(data);
        return this;
    }
    public FilterDomain setUsers(String data){
        super.setUsers(data);
        return this;
    }
    public FilterDomain setLanding_Prelanding(String data){
        super.setModel_ids(data);
        return this;
    }

    public FilterDomain sort(String parameter, Sort sort){
        super.sort(parameter,sort);
        return this;
    }

}
