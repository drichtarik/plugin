package plugin.na.skusku.table.support;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import plugin.na.skusku.basic.MyDependency;

/**
 * The Class SCMEdittingSupport is used so the column SCM URL of the table of the Analyse page can be edited and saved. 
 */
public class SCMEdittingSupport extends EditingSupport {

    private TableViewer viewer;
    private CellEditor editor;

    /**
     * Instantiates a new SCM editting support.
     *
     * @param viewer the viewer
     */
    public SCMEdittingSupport(TableViewer viewer) {
        super(viewer);
        this.viewer = viewer;
        this.editor = new TextCellEditor(viewer.getTable());
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        return editor;
    }

    @Override
    protected boolean canEdit(Object element) {
        return true;
    }

    @Override
    protected Object getValue(Object element) {
      return ((MyDependency) element).getScmURL();
    }

    @Override
    protected void setValue(Object element, Object userInputValue) {
      ((MyDependency) element).setScmURL(String.valueOf(userInputValue));
      viewer.update(element, null);
    }
}