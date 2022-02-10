
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;
import java.util.Locale;

public class BaseChangeFrame extends  JFrame{

    /**
     * text field used for user input of the number to be converted
     */
    private final JTextField number;

    /**
     * text field used for user input of the base the number starts in
     */
    private final JTextField currentBase;

    /**
     * text field used for user input of the base they desire to convert to
     */
    private final JTextField desiredBase;

    /**
     * button for the user to press when they are ready to convert their inputs
     */
    private  final JButton covertButton;

    /**
     * label used to display the output of the conversion
     */
    private final JLabel output;

    /**
     * label used to display to the user when there is an error that occurs
     */
    private final JLabel errorField;

    /**
     * all alphanumerics in order used to check if starting number is a legal number in the current base
     */
    public final static char alphanumeric[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    /**
     * used to construct and handle everything that the frame needs to run correctly
     */
    public BaseChangeFrame(){

        super("Base Change GUI");
        setLayout(new FlowLayout());

        //creates and adds all of the components to the frame
        covertButton = new JButton("convert");
        add(covertButton);

        number = new JTextField("enter current number here");
        add(number);

        currentBase = new JTextField("enter current base here");
        add(currentBase);

        desiredBase = new JTextField("enter base you wish to switch to here");
        add(desiredBase);

        output = new JLabel("output will appear here");
        add(output);

        errorField = new JLabel();
        add(errorField);

        number.addFocusListener(new FocusListener() { // makes the guide text disappear when the number text box is interacted with
            @Override
            public void focusGained(FocusEvent focusEvent) {
                number.setText("");
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });

        currentBase.addFocusListener(new FocusListener() {// makes the guide text disappear when the current base text box is interacted with
            @Override
            public void focusGained(FocusEvent focusEvent) {
                currentBase.setText("");
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });

        desiredBase.addFocusListener(new FocusListener() {// makes the guide text disappear when the desired base text box is interacted with
            @Override
            public void focusGained(FocusEvent focusEvent) {
                desiredBase.setText("");
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });

        covertButton.addActionListener(actionEvent -> { // when the convert button is pressed
            errorField.setText(""); // starts by resetting the error field to avoid confusion
            int startBase, endBase, num;
            try {
                startBase = Integer.parseInt(currentBase.getText()); // checks to make sure the 2 base inputs are integers
                endBase = Integer.parseInt(desiredBase.getText());
                num = Integer.parseInt(number.getText(),startBase);
            }
            catch (NumberFormatException e) { // if either are not integers gives the user an error message and resets the text fields text
                errorField.setText("Error: input only integers into the 2 base text boxes");
                number.setText("enter current number here");
                currentBase.setText("enter current base here");
                desiredBase.setText("enter base you wish to switch to here");
                return;
            }
            if(!validNumberInput()){ // checks to make sure the number input works in the current base and gives error and stops conversion if not
                errorField.setText("Error: starting number not in the correct base (has letters/numbers too high for base)");
                return;
            }
            if (startBase <= 1 || startBase > 32){ // base 1 doesn't make any sense and base 32 is the max the program supports
                errorField.setText("Error: current Base should be number 2-32");
                currentBase.setText("enter current base here");
                return;
            }
            if (endBase <= 1 || endBase > 32){ // checks the same thing for other base input for the same
                errorField.setText("Error: desired base should be a number 2-32");
                desiredBase.setText("enter base you wish to switch to here");
                return;
            }
            String out = "";
            if(startBase == 10){
                while (num > 0){
                    char value = alphanumeric[num%endBase];
                    out = value + out;
                    num = num/endBase;
                }
            }else{
                int inBase10 = 0;
                for(int i = 0; i < number.getText().length(); i++){
                    inBase10 +=  Integer.parseInt(number.getText().substring(i,i+1),startBase) * Math.pow(startBase, number.getText().length() - i - 1);
                }
                while (inBase10 > 0){
                    char value = alphanumeric[inBase10%endBase];
                    out = value + out;
                    inBase10 = inBase10/endBase;
                }
            }
            output.setText(out); //sets the output text to the converted number

        });
    }

    /**
     * checks the text in the number text field to see if it fits with the current base
     * @return true if the inputs are valid, false otherwise
     */
    private boolean validNumberInput(){
        int base = Integer.parseInt(currentBase.getText()); //gets the base in integer form from the current base text box
        int numOfDigits = number.getText().length(); // the number of digits in the number
        int validDigits = 0; //accumulator for number of valid digits
        for(int i = 0; i < numOfDigits; i++){ //goes through each digit
            for(int j = 0; j < base; j++){ // goes through alphanumeric up to the correct part of the base
                if (number.getText().toUpperCase().charAt(i) == alphanumeric[j]){ //checks if the current digit is the same as the current part in alphanumeric
                    validDigits++; // increments the number of correct digits
                    break; // dont need to check anymore in inner loop
                }
            }
            if (validDigits != i+1){ //if not all digits so far are valid can stop and return false
                return false;
            }
        }
        return true; // if it makes it through the double loop can return true
    }


}

