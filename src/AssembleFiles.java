import javax.swing.JOptionPane;
import java.io.*;
import javax.swing.JFileChooser;
import java.util.*;

public class AssembleFiles {

    public static void main( String[] args )
            throws IOException {
        File[] list = new File[100];
        File outFile, selectedFile;
        String w, path;
        int size = 0, returnValue;
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle( "Select a source file" );
        while ( size < list.length ) {
            returnValue = jfc.showOpenDialog(null);
            if ( returnValue == JFileChooser.APPROVE_OPTION ) {
                selectedFile = jfc.getSelectedFile();
                System.out.println(
                        selectedFile.getCanonicalPath() );
                list[size++] = selectedFile;
            }
            w = JOptionPane.showInputDialog(
                    "More filesi (y/n)?" );
            if ( w.startsWith( "n" ) ) break;
        }
        while ( true ) {
            jfc.setDialogTitle( "Select the target file" );
            returnValue = jfc.showSaveDialog(null);
            if ( returnValue == JFileChooser.APPROVE_OPTION ) {
                outFile = jfc.getSelectedFile();
                if ( !outFile.exists() ) break;
                w = JOptionPane.showInputDialog(
                        "The file exists. Overwrite (y/n)?" );
                if ( w.startsWith( "y" ) ) break;
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append( "Saving\n" );
        for ( int i = 0; i < size; i ++ )
            builder.append( String.format( "%2d:%s\n", i,
                    list[i].getCanonicalPath() ) );
        builder.append( "To\n" );
        builder.append( outFile.getCanonicalPath() );
        builder.append( "\nOkay (y/n)?" );
        w = JOptionPane.showInputDialog( builder.toString() );
        if ( w.startsWith( "y" ) ) {
            System.out.println( outFile.getCanonicalPath() );
            PrintStream st = new PrintStream( outFile );
            for ( int i = 0; i < size; i ++ ) {
                st.println( "//--- " + list[i].getCanonicalPath() );
                Scanner sc = new Scanner( list[i] );
                while ( sc.hasNext() ) st.println( sc.nextLine() );
                st.println();
                sc.close();
            }
            st.flush();
            st.close();
        }
        else { System.out.println( "Program terminated." ); }
    }
}
