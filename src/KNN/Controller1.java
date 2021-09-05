package KNN;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class Controller1 implements Initializable {

    @FXML
    private Label lacc,lrecall,lPrecision,lf1;


    @FXML
    private TextField textField1 ,textField2,textField3,textField4,result,acc,recall,precision,f1,acc1,recall1,precision1,f11;

    @FXML
    private Button enter1, enter2, enter3 , enter4,ok,submit;

    ArrayList<Integer>input = new ArrayList<>(5);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void input1(MouseEvent mouseEvent) {
       // System.out.println(textField1.getText());
        String s=textField1.getText();
        input.add(Integer.parseInt(s));
        //System.out.println(input);
    }
    public void input2(MouseEvent mouseEvent) {
        String s=textField2.getText();
        input.add(Integer.parseInt(s));
        //System.out.println(input);
    }
    public void input3(MouseEvent mouseEvent) {
        String s=textField3.getText();
        input.add(Integer.parseInt(s));
      //  System.out.println(input);
    }
    public void input4(MouseEvent mouseEvent) throws IOException {
        String s=textField4.getText();
        input.add(Integer.parseInt(s));
        KNNCalculation k = new KNNCalculation(3,input.get(0),input.get(1),input.get(2),input.get(3));
        Modifyedknn m = new Modifyedknn(2,10);
        result.setText(k.result());
    }

    public void show(MouseEvent e) throws IOException {
        lacc.setVisible(true);
        acc.setVisible(true);
        lrecall.setVisible(true);
        acc1.setVisible(true);
        lPrecision.setVisible(true);
        precision1.setVisible(true);
        lf1.setVisible(true);
        f11.setVisible(true);
        Modifyedknn m = new Modifyedknn(3,10);
        acc.setText(String.format("%.2f", m.finalAccuracy())+"%");
       // recall1.setText(String.format("%.2f", m.recall())+"%");
        precision1.setText(String.format("%.2f",m.precision())+"%");
        f11.setText(String.format("%.2f",m.f1Score())+"%");

         acc1.setText(String.format("%.2f", m.recall())+"%");
    }

}
