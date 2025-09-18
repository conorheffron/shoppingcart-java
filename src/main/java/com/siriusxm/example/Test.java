package com.siriusxm.example;

public class Test {

    static String s = "<td><a href=\"/OffalyCC/AppFileRefDetails/001006/0\">001006</a></td>\n" +
            "<td>APPLICATION FINALISED</td>\n" +
            "                <td>10/12/2000</td>\n" +
            "                <td>07/12/2000</td>\n" +
            "                <td>CONDITIONAL</td>\n" +
            "                <td>19/09/2000</td>\n" +
            "                <td>JAMES &amp; ANN HUNSTON</td>\n" +
            "                <td>FR PAUL MURPHY STREET<br>EDENDERRY<br><br><br></td>\n" +
            "                <td>RETENTION OF TWO NO WINDOWS &amp; ROOF STRUCTURE AT GABLE END...</td>\n" +
            "                <td>OFFALY CO. CO.</td>\n" +
            "                ";

    public static void main( String[] args ) {
        String[] split = s.split( "\n" );
        for( String value : split ) {
            String d = value;
            if (d.length() > 0) {
                if (d.contains( "<td>" ))
                    d = d.substring( d.indexOf( "<td>" ) + 4, d.indexOf( "</td>" ) );
                d = d.replaceAll( "&amp;", "&" );
                System.out.println(d);
            }
        }
    }
}
