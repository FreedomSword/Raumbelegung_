package SWT2;

import java.util.ArrayList;
import java.util.List;

public class Nachricht {

    String Nachricht;

    public Nachricht() {

    }

    public String nachrichtUmwandeln(String input) {

        boolean save = false;
        char[] ch = new char[input.length()];
        char[] output  = new char[ch.length];

        for(int i = 0; i < input.length(); i++)  {
            ch[i] = input.charAt(i);
    }
        int a = 0;
        for(int i = 0; i < ch.length; i++) {

            if (save == false) {
                if (ch[i] == ':') {
                    save = true;
                } else {

                }
            } else {
                if(ch[i] == '}')
                {

                }
                else{
                    output[a] = ch[i];
                    a++;
                }
                }


        }


        return String.valueOf(output).trim();
        }



}

