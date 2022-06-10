package SWT2;

public class Message {

    String message;

    public Message() {

    }

    public String convertMessage(String input) {



        boolean save = false;

        //Char Array for the Input
        char[] ch = input.toCharArray();

        //Char Array for the Output
        char[] output  = new char[ch.length];

        int a = 0;

        for(int i = 0; i < ch.length; i++) {

            //Check over "save" is true

            if (save == false) {

                //Check over the char should be saved
                //After every ":" the values are located
                //Set save as true
                if (ch[i] == ':') {
                    save = true;
                }

                //Do nothing if its not a ":"
                else {

                }

            }

            //Save the chars in outpt array if save is true
            else {
                //Dont save "}"
                if(ch[i] == '}')
                {

                }
                else{
                    output[a] = ch[i];
                    a++;
                }
            }

        }

        //Return the char Array as String
        //Trim the char Array after the last value so there are no whitespaces / null
        return String.valueOf(output).trim();
        }



}

