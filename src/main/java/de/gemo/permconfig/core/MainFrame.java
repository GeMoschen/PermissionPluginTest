package de.gemo.permconfig.core;

import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import de.gemo.permconfig.impl.Subject;
import de.gemo.permconfig.impl.SubjectPlayer;
import de.gemo.permconfig.impl.WorldDataHolder;
import de.gemo.permconfig.services.PermissionHolder;
import de.gemo.permconfig.utils.FileUtils;
import de.gemo.permconfig.utils.JsonUtils;

/**
 * Created by GeMo on 16.05.2015.
 */
public class MainFrame extends JFrame {

    private static final long serialVersionUID = -5024022248163814549L;

    private int[] dimension = {800, 600};

    private int[] position = {-1, -1};

    public MainFrame() {
        // startup
        super("PermissionsConfigurator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set layout
        this.setLayout(new GridLayout(0, 1));

        // set unresizeable
        this.setResizable(false);

        // load config
        this.loadConfig();

        // create UI
        this.createUI();

        // pack & show
        this.pack();
        this.setVisible(true);

        // set size
        this.setSize(dimension[0], dimension[1]);

        // set position
        if (this.position[0] == -1 && this.position[1] == -1) {
            this.setLocationRelativeTo(null);
        } else {
            this.setLocation(this.position[0], this.position[1]);
        }

        // add listener
        this.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {

            }

            public void windowClosing(WindowEvent e) {
                MainFrame.this.dimension[0] = MainFrame.this.getWidth();
                MainFrame.this.dimension[1] = MainFrame.this.getHeight();
                MainFrame.this.position[0] = MainFrame.this.getX();
                MainFrame.this.position[1] = MainFrame.this.getY();
                MainFrame.this.saveConfig(new File("app.conf"));
                PermissionHolder.getInstance().saveWorlds();
            }

            public void windowClosed(WindowEvent e) {

            }

            public void windowIconified(WindowEvent e) {

            }

            public void windowDeiconified(WindowEvent e) {

            }

            public void windowActivated(WindowEvent e) {

            }

            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    private void createUI() {
        // Create the nodes.
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Worlds");
        createNodes(top);

        // Create a tree that allows one selection at a time.
        final JTree tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        // Listen for when the selection changes.
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

                if (node == null)
                    return;

                Object nodeInfo = node.getUserObject();
//                if (node.isLeaf()) {
                System.out.println("SELECTED: " + nodeInfo);
//                }

            }
        });

        // Create the scroll pane and add the tree to it.
        JScrollPane treeView = new JScrollPane(tree);
        this.add(tree);
    }

    private void createNodes(DefaultMutableTreeNode top) {
        PermissionHolder.getInstance().loadWorlds();

        final Collection<WorldDataHolder> worlds = PermissionHolder.getInstance().getDataHolder().getAll();
        for (WorldDataHolder dataHolder : worlds) {
            DefaultMutableTreeNode worldNode = new DefaultMutableTreeNode(dataHolder);
            top.add(worldNode);

            // groups
            fillGroups(dataHolder, worldNode);

            // players
            fillPlayers(dataHolder, worldNode);
        }
    }

    private void addPermissions(DefaultMutableTreeNode topNode, Subject subject) {
        List<String> permissionNodes = subject.getWhitelistNodes();
        if (!permissionNodes.isEmpty()) {
            for (String permission : permissionNodes) {
                DefaultMutableTreeNode permissionNode = new DefaultMutableTreeNode("+ " + permission);
                topNode.add(permissionNode);
            }
        }
        permissionNodes = subject.getBlacklistNodes();
        if (!permissionNodes.isEmpty()) {
            for (String permission : permissionNodes) {
                DefaultMutableTreeNode permissionNode = new DefaultMutableTreeNode("- " + permission);
                topNode.add(permissionNode);
            }
        }
    }

    private void fillPlayers(WorldDataHolder dataHolder, DefaultMutableTreeNode worldNode) {
        DefaultMutableTreeNode playersNode = new DefaultMutableTreeNode("[ Players ]");
        worldNode.add(playersNode);

        final Collection<SubjectPlayer> players = dataHolder.getPlayers();
        for (Subject subject : players) {
            DefaultMutableTreeNode playerNode = new DefaultMutableTreeNode("UUID = " + subject);
            playersNode.add(playerNode);

            if (subject.hasParent()) {
                playerNode.add(new DefaultMutableTreeNode("Group = " + subject.getParent()));
            } else {
                playerNode.add(new DefaultMutableTreeNode("Group = {NULL}"));
            }

            if (!subject.getBlacklistNodes().isEmpty() || !subject.getWhitelistNodes().isEmpty() || subject.hasParent()) {
                DefaultMutableTreeNode permissionNode = new DefaultMutableTreeNode("[ Permissions ]");
                playerNode.add(permissionNode);
                this.addPermissions(permissionNode, subject);
                if (subject.hasParent()) {
                    DefaultMutableTreeNode inhertitedNode = new DefaultMutableTreeNode("[ Inherited ]");
                    permissionNode.add(inhertitedNode);
                    while (subject.hasParent()) {
                        subject = subject.getParent();
                        this.addPermissions(inhertitedNode, subject);
                    }
                }
            }
        }
    }

    private void fillGroups(WorldDataHolder dataHolder, DefaultMutableTreeNode worldNode) {
        DefaultMutableTreeNode groupsNode = new DefaultMutableTreeNode("[ Groups ]");
        worldNode.add(groupsNode);

        final Collection<Subject> groups = dataHolder.getGroups();
        for (Subject group : groups) {
            DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group);
            groupsNode.add(groupNode);

            DefaultMutableTreeNode permissionNode = new DefaultMutableTreeNode("[ Permissions ]");
            groupNode.add(permissionNode);

            this.addPermissions(permissionNode, group);
            if (group.hasParent()) {
                DefaultMutableTreeNode inhertitedNode = new DefaultMutableTreeNode("[ Inherited ]");
                permissionNode.add(inhertitedNode);

                while (group.hasParent()) {
                    group = group.getParent();
                    this.addPermissions(inhertitedNode, group);
                }
            }
        }
    }

    private void loadConfig() {
        final File configFile = new File("app.conf");
        if (configFile.exists()) {
            System.out.println("[ INFO ] Loading 'app.conf'...");
            try {
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse(FileUtils.readFile(configFile)).getAsJsonObject();

                this.dimension[0] = object.get("app.window.width").getAsInt();
                this.dimension[1] = object.get("app.window.height").getAsInt();
                this.position[0] = object.get("app.window.left").getAsInt();
                this.position[1] = object.get("app.window.top").getAsInt();
            } catch (Exception e) {
                e.printStackTrace();
                saveConfig(configFile);
            }
        } else {
            System.out.println("'app.conf' not found...");
            saveConfig(configFile);
        }
    }

    private void saveConfig(File configFile) {
        System.out.println("[ INFO ] Saving 'app.conf'...");

        JsonObject object = new JsonObject();
        object.add("app.window.width", new JsonPrimitive(this.dimension[0]));
        object.add("app.window.height", new JsonPrimitive(this.dimension[1]));
        object.add("app.window.left", new JsonPrimitive(this.position[0]));
        object.add("app.window.top", new JsonPrimitive(this.position[1]));

        FileUtils.saveString(JsonUtils.prettyFormat(object), configFile);
    }

}
