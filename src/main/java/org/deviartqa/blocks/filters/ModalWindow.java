package org.deviartqa.blocks.filters;

import org.deviartqa.core.SiteBlock;
import org.deviartqa.core.Widget;

public class ModalWindow extends SiteBlock {

    public void readyBlock(){
        checkBlock(page.locator("//div[@class='md-content']"));
    }
    String x_path_locator = "";

    public ModalWindow clickStatus_operator_processing(){
        super.clickStatus_operator_processing();
        return this;
    }

    public ModalWindow clickSaveButton(){
        super.clickSaveButton();
        return this;
    }

    public void save(boolean save){
        if (save){
            new Widget(page.locator("//div[@id='result-"+x_path_locator+"-stick-top']//button[@name='ShippingFormCallRo[result]']")).click();
        }else {
            new Widget(page.locator("//div[@id='result-"+x_path_locator+"-stick-top']//a")).click();
        }
    }

    public ModalWindow callProcess(CallResult callResult, ResultAdditional resultAdditional, String comment, OperatorStatus operatorStatus){
        setCallResult(callResult);
        setResultAdditional(callResult,resultAdditional);
        if (comment.toCharArray().length > 0){
            new Widget(page.getByTestId("ShippingFormCallRo[comment]")).fill(comment);
        }
        setOperatorStatus(operatorStatus);
        return this;
    }

    public ModalWindow callProcess_busyWithTime(String time,String comment, OperatorStatus operatorStatus){
        setCallResult(CallResult.busy);
        setResultAdditional(CallResult.busy,ResultAdditional.busy_SelectCallTime);
        Widget set_time = new Widget(page.getByTestId("ShippingFormCallRo[next_call_time]"));
        set_time.element.evaluate("(el, value) => el.setAttribute('value', value)", time);
        setComment(comment);
        setOperatorStatus(operatorStatus);
        return this;
    }

    public ModalWindow setComment(String comment){
        if (comment.toCharArray().length > 0){
            new Widget(page.getByTestId("ShippingFormCallRo[comment]")).fill(comment);
        }

        return this;
    }

    public ModalWindow setCallResult(CallResult callResult){
        if (callResult == CallResult.approve){
            x_path_locator = "success";
        }else if (callResult == CallResult.techProblem){
            x_path_locator = "technical-problems";
        }else {
            x_path_locator = callResult.toString();
        }

        new Widget(page.locator("//a[@data-modal='result-"+x_path_locator+"-stick-top']")).click();

        return this;
    }

    private void setResultAdditional(CallResult callResult, ResultAdditional resultAdditional){
        if (callResult == CallResult.trash || callResult == CallResult.busy) {
            new Widget(page.locator("//div[@name='ShippingFormCallRo[result_additional]']/button")).click();
            new Widget(page.locator("//div[@name='ShippingFormCallRo[result_additional]']//li[@data-original-index=\""+resultAdditional.value+"\"]")).click();
        }
    }

    public ModalWindow setOperatorStatus(OperatorStatus operatorStatus){
        Widget operStatus = new Widget(page.locator("//div[@id='result-"+x_path_locator+"-stick-top']//input[@type='radio'][@value="+operatorStatus.value+"]/.."));
        if (!operStatus.element.getAttribute("class").contains("active")){
            operStatus.click();
        }

        return this;
    }



    public enum OperatorStatus{
        continueProcessing(1),pause(3),lunch(4),training(5),techProblem(6),stopProcessing(2);
        private int value;
        OperatorStatus(int i) {
            value = i;
        }
    }

    public enum CallResult{
        approve,busy,reject,trash,techProblem
    }

    public enum ResultAdditional {
        busy_NoAnswer(0),busy_ThrownOff(1),busy_OutsideTheZone(2),busy_SelectCallTime(3),
        trash_NotOrdered(0),trash_DuplicateOrder(1),trash_wrongNumber(2),trash_test(3),trash_rudeness(6);
        private int value;
        ResultAdditional(int i) {
            value = i;
        }
    }

}
