package plugin.na.skusku.wizard.pages;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * The Class Build is the last page of the wizard to display the data of the
 * resulting builds.
 */
public class Build extends WizardPage {

	private Composite container;
	private Group grpDependencyDetails;
	private Table table;
	private TableViewer tableViewer;

	/**
	 * Create the wizard.
	 */
	public Build() {
		super("Page two");
		setTitle("Dependency Analysis");
		setDescription("Build results");
	}

	/**
	 * Create contents of the wizard.
	 *
	 * @param parent
	 *            the parent
	 */
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new FormLayout());

		setControl(container);

		grpDependencyDetails = new Group(container, SWT.NONE);
		FormData fd_grpDependencyDetails = new FormData();
		fd_grpDependencyDetails.bottom = new FormAttachment(100, -38);
		fd_grpDependencyDetails.right = new FormAttachment(100, -10);
		fd_grpDependencyDetails.top = new FormAttachment(0, 10);
		fd_grpDependencyDetails.left = new FormAttachment(0, 10);
		grpDependencyDetails.setLayoutData(fd_grpDependencyDetails);
		grpDependencyDetails.setText("Dependency details");
		grpDependencyDetails.setLayout(new FormLayout());

		createViewer(parent);
	}

	public void createViewer(Composite parent) {
		tableViewer = new TableViewer(grpDependencyDetails,
				SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

		table = tableViewer.getTable();

		tableViewer.setContentProvider(new ArrayContentProvider());

		FormData fd_table = new FormData();
		fd_table.bottom = new FormAttachment(100, -5);
		fd_table.right = new FormAttachment(100, -5);
		fd_table.top = new FormAttachment(0, 5);
		fd_table.left = new FormAttachment(0, 5);

		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	/**
	 * Can finish. If the page Finish button can be pressed.
	 *
	 * @return true
	 */
	public Boolean canFinish() {
		return true;
	}

}
