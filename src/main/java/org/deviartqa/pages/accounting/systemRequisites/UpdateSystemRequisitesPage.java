package org.deviartqa.pages.accounting.systemRequisites;

public class UpdateSystemRequisitesPage extends CreateSystemRequisitesPage {

    public UpdateSystemRequisitesPage open(int id) {
        openPage("/acp/accounting/systemRequisites/modify?id="+id);
        return this;
    }

}
