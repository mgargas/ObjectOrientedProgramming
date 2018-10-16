package pl.edu.agh.iisg.to.javafx.cw2.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import pl.edu.agh.iisg.to.javafx.cw2.model.Category;
import pl.edu.agh.iisg.to.javafx.cw2.model.Transaction;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

public class TransactionEditDialogPresenter {

	private Transaction transaction;

	@FXML
	private TextField dateTextField;

	@FXML
	private TextField payeeTextField;

	@FXML
	private TextField categoryTextField;

	@FXML
	private TextField inflowTextField;
	
	private Stage dialogStage;
	
	private boolean approved;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setData(Transaction transaction) {
		this.transaction = transaction;
		updateControls();
	}
	
	public boolean isApproved() {
		return approved;
	}
	
	@FXML
	private void handleOkAction(ActionEvent event) {
		// TODO: implement
        if(isInputValid()){
            updateModel();
            approved = true;
            dialogStage.close();
        }
	}
	
	@FXML
	private void handleCancelAction(ActionEvent event) {
		// TODO: implement
        dialogStage.close();
	}
	
	private boolean isInputValid() {
		// TODO: implement
		return true;
	}
	
	private void updateModel() {
		// TODO: implement
        transaction.setCategory(new Category(categoryTextField.getText()));
        transaction.setPayee(payeeTextField.getText());
        DecimalFormat decimalFormatter = new DecimalFormat();
        decimalFormatter.setParseBigDecimal(true);
        try {
            transaction.setInflow((BigDecimal)decimalFormatter.parse(inflowTextField.getText()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateStringConverter converter = new LocalDateStringConverter(formatter, formatter);
        transaction.setDate(converter.fromString(dateTextField.getText()));



	}
	
	private void updateControls() {
		// TODO
        inflowTextField.setText(transaction.getInflow().toString());
        categoryTextField.setText(transaction.getCategory().toString());
        payeeTextField.setText(transaction.getPayee());
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateStringConverter converter = new LocalDateStringConverter(formatter, formatter);
        dateTextField.setText(converter.toString(transaction.getDate()));

	}
}
