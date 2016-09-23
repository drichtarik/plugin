package plugin.na.skusku.wizard.pages;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import plugin.na.skusku.basic.MyDependency;
import plugin.na.skusku.basic.PomActions;
import plugin.na.skusku.table.support.SCMEdittingSupport;
import plugin.na.skusku.table.support.SCMRevEdittingSupport;

/**
 * The Class Analyse is for Second page of the Wizard.
 */
public class Analyse extends WizardPage {

	private Composite container;
	private Group grpDependencyDetails;
	private Table table;
	private TableViewer tableViewer;
	private Label lblBuildScript;
	private Text text_3;

	private Button btnDependencyAnalysis;
	private Group grpBuild;
	private Group grpAnalysis;
	private Label lblInOrderTo;

	private PomActions actions = new PomActions();

	/**
	 * Create the wizard.
	 */
	public Analyse() {
		super("Page two");
		setTitle("Dependency Analysis");
		setDescription("Analyzed lists of project dependencies");
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
		fd_grpDependencyDetails.left = new FormAttachment(0, 10);
		fd_grpDependencyDetails.right = new FormAttachment(100, -10);
		grpDependencyDetails.setLayoutData(fd_grpDependencyDetails);
		grpDependencyDetails.setText("Dependency details");
		grpDependencyDetails.setLayout(new FormLayout());
		{
			fd_grpDependencyDetails.top = new FormAttachment(0, 64);
		}
		{
			grpBuild = new Group(container, SWT.NONE);
			fd_grpDependencyDetails.bottom = new FormAttachment(grpBuild, -6);
			grpBuild.setText("Build");
			FormData fd_grpBuild = new FormData();
			fd_grpBuild.top = new FormAttachment(0, 305);
			fd_grpBuild.bottom = new FormAttachment(100);
			fd_grpBuild.left = new FormAttachment(0, 10);
			fd_grpBuild.right = new FormAttachment(100, -10);
			grpBuild.setLayoutData(fd_grpBuild);
			text_3 = new Text(grpBuild, SWT.BORDER);
			text_3.setBounds(80, 7, 346, 19);

			Button btnCreateBc = new Button(grpBuild, SWT.NONE);
			btnCreateBc.setBounds(433, 3, 113, 28);
			btnCreateBc.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println("Trigger Build(s) button selected!");

				}
			});
			btnCreateBc.setText("Trigger Build(s)");
			{
				lblBuildScript = new Label(grpBuild, SWT.NONE);
				lblBuildScript.setBounds(10, 10, 65, 14);
				lblBuildScript.setText("Build Script");
			}
		}

		grpAnalysis = new Group(container, SWT.NONE);
		grpAnalysis.setText("Analysis");
		FormData fd_grpAnalysis = new FormData();
		fd_grpAnalysis.right = new FormAttachment(grpDependencyDetails, 0, SWT.RIGHT);
		fd_grpAnalysis.top = new FormAttachment(0, 5);
		fd_grpAnalysis.bottom = new FormAttachment(grpDependencyDetails, -6);
		fd_grpAnalysis.left = new FormAttachment(0, 10);
		grpAnalysis.setLayoutData(fd_grpAnalysis);
		btnDependencyAnalysis = new Button(grpAnalysis, SWT.NONE);
		btnDependencyAnalysis.setBounds(319, 3, 145, 28);
		btnDependencyAnalysis.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				actions.setJackson();
				actions.getDa();

				tableViewer.setContentProvider(new ArrayContentProvider());
				tableViewer.setInput(actions.getGavDependencies());
				tableViewer.refresh();

			}
		});
		btnDependencyAnalysis.setText("Dependency Analysis");

		lblInOrderTo = new Label(grpAnalysis, SWT.NONE);
		lblInOrderTo.setBounds(10, 10, 303, 14);
		lblInOrderTo.setText("In order to perform the analysis click the following button:");

		createViewer(parent);
	}

	public void createViewer(Composite parent) {
		tableViewer = new TableViewer(grpDependencyDetails,
				SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		createColumns(parent, tableViewer);

		table = tableViewer.getTable();

		actions.projectPom();
		actions.pomToMavenProject();
		actions.setDependencies();

		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(actions.getDependencies());

		FormData fd_table = new FormData();
		fd_table.bottom = new FormAttachment(100, -5);
		fd_table.right = new FormAttachment(100, -5);
		fd_table.top = new FormAttachment(0, 5);
		fd_table.left = new FormAttachment(0, 5);

		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}

	public void createColumns(final Composite parent, final TableViewer viewer) {

		String[] titles = { "Dependency", "Version", "Avalilable versions in PNC","Best Match version Available in PNC", "SCM Revision", "SCM Repository" };
		int[] bounds = { 100, 60, 230, 230, 100, 150 };

		// first column is for the Dependency name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				MyDependency dep = (MyDependency) element;
				return dep.getGroupId() + ":" + dep.getArtifactId();
			}
		});

		// second column is for the Version
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				MyDependency dep = (MyDependency) element;
				return dep.getVersion();
			}
		});
		
		// third column is for the PNC available versions
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				MyDependency dep = (MyDependency) element;
				return dep.getAvailableVersions().toString();
			}
		});

		// third column is for the PNC best match version
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				MyDependency dep = (MyDependency) element;
				return dep.getBestMatchVersion().toString();
			}
		});

		// fourth column is for the SCM revision
		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((MyDependency) cell.getElement()).getScmRev());
			}
		});
		col.setEditingSupport(new SCMRevEdittingSupport(viewer));

		// fifth column is for the SCM URL
		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((MyDependency) cell.getElement()).getScmURL());
			}
		});
		col.setEditingSupport(new SCMEdittingSupport(viewer));

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
	 * Can finish is for Finish button.
	 *
	 * @return true, if successful
	 */
	public boolean canFinish() {
		return true;
	}

}