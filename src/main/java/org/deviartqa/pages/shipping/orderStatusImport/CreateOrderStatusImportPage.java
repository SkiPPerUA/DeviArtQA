package org.deviartqa.pages.shipping.orderStatusImport;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;

public class CreateOrderStatusImportPage extends CabinetPage {

    public CreateOrderStatusImportPage (){
        pageLoc = "ShippingFormOrderStatusImport";
    }

    public CreateOrderStatusImportPage readyPage() {
        checkPage(Locators.save);
        return this;
    }

    public CreateOrderStatusImportPage open() {
        openPage("/acp/shipping/orderStatusImport/new");
        return this;
    }

    public CreateOrderStatusImportPage setFile(String path){
        super.setFile(path);
        return this;
    }

    public CreateOrderStatusImportPage setName(String data){
        super.setName(data);
        return this;
    }

    public CreateOrderStatusImportPage setDelivery_service_id(String data){
        super.setDelivery_service_id(data);
        return this;
    }

    public CreateOrderStatusImportPage setOrder_status_type(String data){
        super.setOrder_status_type(data);
        return this;
    }

    public CreateOrderStatusImportPage setOrder_status(String data){
        super.setOrder_status(data);
        return this;
    }

    public CreateOrderStatusImportPage choseAccounting_system_requisites_account_id(String data){
        super.choseAccounting_system_requisites_account_id(data);
        return this;
    }

    public CreateOrderStatusImportPage setCost(String data){
        super.setCost(data);
        return this;
    }

    public CreateOrderStatusImportPage setInvoice(String data){
        super.setInvoice(data);
        return this;
    }

    public UpdateOrderStatusImportPage clickSaveButton(){
        Locators.save.click();
        return new UpdateOrderStatusImportPage();
    }
}
