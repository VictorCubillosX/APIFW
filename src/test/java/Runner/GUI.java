package Runner;

import excelManager.ReadExcelFile;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.io.IOException;

public class GUI {

    public String[] showGui() throws IOException {
        javax.swing.JFileChooser jfc = new javax.swing.JFileChooser();
        String path = "";
        String fileName = "";
        String[] tcSelected;
        String[] dataReturn = new String[3];
        try {
            if(jfc.showSaveDialog(null) == jfc.APPROVE_OPTION){
                path = jfc.getSelectedFile().getPath();
                fileName = jfc.getSelectedFile().getName();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        ReadExcelFile excel = new ReadExcelFile();

        //Window to select test case

        /** The array namesTc save to names the Test cases */

        Object[] objArr = excel.getSheets(path).toArray();
        String[] namesTc = new String[objArr.length];
        System.arraycopy(objArr, 0, namesTc, 0, objArr.length);

        JRadioButton[] radio = new JRadioButton[namesTc.length];
        //create panel interface
        JPanel gui = new JPanel(new BorderLayout(1,1));
        JPanel panel = new JPanel(new GridLayout(0,1));
        JScrollPane scroll = new JScrollPane(panel);
        //scroll.setBounds(5,10,140,100);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(100,200));
        gui.add(scroll);
        
        JLabel label = new JLabel();
        label.setText("Which Test Cases wish execute");
        gui.add(label, BorderLayout.NORTH);
        /** This for create and add to array radio all the buttons that we will show */
        String name = "";
        for (int u = 0; u < namesTc.length; u++) {

            name = namesTc[u];
            if (name != null) {
                JRadioButton button = new JRadioButton(name);
                radio[u] = button;
                panel.add(radio[u]);
            }

            if(radio[u] == null) {
                panel.remove(radio[u]);
            }

        }

        
        /** Show the Panel */
        JOptionPane.showMessageDialog(null, gui);       
        
        /** This for save the selected Test cases */
        tcSelected = new String[radio.length];
        String radios="";
        for (int t = 0; t < radio.length; t++) {

            if (radio[t].isSelected()) {

            	radios += radio[t].getText()+"-";

            }
        }

        dataReturn[0] = path;
        dataReturn[1] = fileName;
        dataReturn[2] = radios;

        return dataReturn;


    }

}
