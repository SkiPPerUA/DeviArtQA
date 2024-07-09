package org.deviartqa.pages.system.autoConfirm;

public class UpdateAutoconfirmPage extends CreateAutoconfirmPage {

    public UpdateAutoconfirmPage(){
        pagePoint = "leadAutoconfirm";
        pageLoc = "AcpLeadAutoconfirm";
    }

    public UpdateAutoconfirmPage open(int id){
        super.openPage("/acp/"+pagePoint+"/modify?id="+id);
        return this;
    }
}
