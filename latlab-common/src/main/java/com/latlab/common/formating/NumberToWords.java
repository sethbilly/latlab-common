/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.formating;


import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class NumberToWords 
{
    private static final Logger LOGGER = Logger.getLogger(NumberToWords.class.getSimpleName());
    
//    private static String MAJOR_CURRENCY;
//    private static String MINOR_CURRENCY;
    
    private String majorCurrency = "Ghana Cedis";;
    private String minorCurrency = "Pesewas";
    
    private String decimalValue;
    private String wholeValue;
    
    private StringBuilder sb = new StringBuilder();

    public NumberToWords()
    {
    }
    
    public NumberToWords(String majorCurrency, String minorCurrency)
    {
        this.majorCurrency = majorCurrency;
        this.minorCurrency = minorCurrency;
    }
    
    
    
    public static NumberToWords getInstance(String majorCurrency, String minorCurrency) 
    {
        NumberToWords numberToWords = new NumberToWords();
        numberToWords.majorCurrency = majorCurrency;
        numberToWords.minorCurrency = minorCurrency;
        
        return numberToWords;
    }
    
    public static NumberToWords getInstance() 
    {
        NumberToWords numberToWords = new NumberToWords();
        numberToWords.majorCurrency = "Ghana Cedis";
        numberToWords.minorCurrency = "Pesewas";
        
        return numberToWords;
    }
    
    public String convertToWords(double value)
    {
        value = Math.abs(value);
        processWholePart(value);
        processDecimalPart();
        
        return sb.toString();
    }
    
    private void processDecimalPart()
    {
        int val = Integer.parseInt(decimalValue);
        
        if(val == 0)
        {
            return;
        }
        
        String exist = Figures.find(val);
        
        if(exist != null)
        {
            sb.append(exist);
        }
        else
        {
            if(!String.valueOf(decimalValue.charAt(0)).equals("0"))
            {
                String ss = decimalValue.charAt(0) + "0";
                sb.append(Figures.find(Integer.parseInt(ss)));
                sb.append(Figures.find(Integer.parseInt(String.valueOf(decimalValue.charAt(1)))));
            }
        }
        
        sb.append(minorCurrency);
    }
    
    private String[] splitWholePart(double value)
    {
        String svalue = fm().format(value);
        System.out.println(svalue);
        try 
        {
            String splitString[] = svalue.split("\\.");
            
            if(splitString.length > 1)
            {
                wholeValue = splitString[0];
                decimalValue = splitString[1];
            }
            else
            {
                wholeValue = svalue;
            }
            
            return wholeValue.split(",");
        }
        catch (Exception e) 
        {
            LOGGER.log(Level.SEVERE, "Error getting whole and decimal part", e);
        }
        
        return null;
    }
    
    private NumberFormat fm()
    {
        NumberFormat fm = NumberFormat.getInstance();
        fm.setMinimumFractionDigits(2);
        fm.setMaximumFractionDigits(2);
        
        return fm;
    }
    
    private void processWholePart(double value)
    {
        String[] wholeList = splitWholePart(value); 
       
        int counter = wholeList.length;
        double firstVal = counter > 0 ? Double.parseDouble(wholeList[0]) : 0.0;
        
        for (int i=0; i<wholeList.length; i++) 
        {
            String svalue = String.valueOf(wholeList[i]);
            svalue = Integer.parseInt(svalue) + "";
            
//            System.out.println("sval: " + svalue);
            
            if(svalue.equals("0"))
            {
                counter--;
                continue;
            }
            
            if(svalue.length() == 3)
            {
                sb.append(Figures.find(Integer.parseInt(String.valueOf(svalue.charAt(0)))));
                sb.append(Division.find(0));
                
                int val = Integer.parseInt(svalue.substring(1, 3));
                
                if(val != 0)
                {
                    sb.append("and ");
                }
                
                String exist = Figures.find(val);
                
                if(exist != null && !exist.equals(Figures.ZERO.getLabel()))
                {
                    sb.append(exist);
                }
                else
                {
                    if(!String.valueOf(svalue.charAt(1)).equals("0"))
                    {
                        String ss = svalue.charAt(1) + "0";
                        sb.append(Figures.find(Integer.parseInt(ss)));
                        sb.append(Figures.find(Integer.parseInt(String.valueOf(svalue.charAt(2)))));
                    }
                }
            }
            else
            {
                int val = Integer.parseInt(svalue);
                String exist = Figures.find(val);
                
                if(i>0)
                {
                    sb.append("and ");
                }
                
                if(exist != null && !exist.equals(Figures.ZERO.getLabel()))
                {
                    sb.append(exist);
                }
                else
                {
                    if(!String.valueOf(svalue.charAt(0)).equals("0"))
                    {
                        System.out.println("came here....");
                        String ss = svalue.charAt(0) + "0";
                        sb.append(Figures.find(Integer.parseInt(ss)));
                        sb.append(Figures.find(Integer.parseInt(String.valueOf(svalue.charAt(1)))));
                    }
                }
            }
            
            System.out.println("count: " + counter);
            
            if(counter > 1)
            {
                sb.append(Division.find(counter));
            }
                
            counter--;
        }
        
        int decimalNumber = Integer.parseInt(decimalValue);
        
        if(decimalNumber == 0)
        {
            sb.append(majorCurrency);
            return;
        }
        
        if(firstVal > 0 && (decimalNumber > 0))
        {
            sb.append(majorCurrency).append(", ");
        }
    }
    
    
    public static enum Division
    {

        HUNDRED_("Hundred ", 0),
        HUNDRED("Hundred ", 1),
        THOUSAND("Thousand ", 2),
        MILLION("Million ", 3),
        BILLION("Billion ", 4),
        TRILLION("Trillion ", 5);

        private Division(String label, int length)
        {
            this.label = label;
            this.length = length;
        }
        private String label;
        private int length;

        @Override
        public String toString()
        {
            return label;
        }

        public String getLabel()
        {
            return label;
        }

        public void setLabel(String label)
        {
            this.label = label;
        }

        public int getLength()
        {
            return length;
        }

        public void setLength(int length)
        {
            this.length = length;
        }
        
        public static String find(int length)
        {
            Division[] divisions = Division.values();

            for (Division division : divisions)
            {
                if (division.getLength() == length)
                {
                    return division.getLabel();
                }
            }
            return null;
        }
    }
    
    public static enum Figures 
    {
        ZERO("Zero ", 0),
        ONE("One ", 1),
        TWO("Two ", 2),
        THREE("Three ", 3),
        FOUR("Four ", 4),
        FIVE("Five ", 5),
        SIX("Six ", 6),
        SEVEN("Seven ", 7),
        EIGHT("Eight ", 8),
        NINE("Nine ", 9),
        TEN("Ten ", 10), 
        ELEVEN("Eleven ", 11), 
        TWELVE("Twelve ", 12), 
        THIRTEEN("Thirteen ", 13), 
        FOURTEEN("Fourteen ", 14), 
        FIFTEEN("Fifteen ", 15), 
        SIXTEEN("Sixteen ", 16), 
        SEVENTEEN("Seventeen ", 17), 
        EIGHTEEN("Eighteen ", 18), 
        NINETEEN("Nineteen ", 19), 
        TWENTY("Twenty ", 20), 
        THIRTY("Thirty ", 30), 
        FOURTY("Forty ", 40), 
        FIFTY("Fifty ", 50), 
        SIXTY("Sixty ", 60), 
        SEVENTY("Seventy ", 70),
        EIGHTY("Eighty ", 80),
        NINETY("Ninety ", 90);

        Figures(String label, int intValue) 
        {
            this.label = label;
            this.intValue = intValue;
        }

        private String label;
        private int intValue;

        public static String find(int value)
        {
            Figures[] figures = Figures.values();

            for (Figures fig : figures) 
            {
                if(fig.getIntValue() == value)
                {
                    return fig.getLabel();
                }
            }

            return null;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getIntValue() {
            return intValue;
        }

        public void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        @Override
        public String toString() 
        {
            return label;
        }
    }
}
