import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;
import java.util.List;

public class ProgramTableModel extends AbstractTableModel {

    protected  String[] columnNames={"Proceso","TCC","TE","TVC","TB","TT","TI","TF"};

    private LinkedList<Proceso> data;

    public ProgramTableModel(LinkedList<Proceso> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:return data.get(rowIndex).nombre;
            case 1:return data.get(rowIndex).tCambioDeContexto;
            case 2:return data.get(rowIndex).tEjecucion;
            case 3:return data.get(rowIndex).tVencimientoDeCuantum;
            case 4:return data.get(rowIndex).tBloqueo;
            case 5:return data.get(rowIndex).tTotal;
            case 6:return data.get(rowIndex).tInicial;
            case 7:return data.get(rowIndex).tFinal;
            default: return 0;
        }
    }
}
