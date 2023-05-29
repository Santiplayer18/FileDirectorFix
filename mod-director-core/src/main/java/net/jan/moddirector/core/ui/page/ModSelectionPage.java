package net.jan.moddirector.core.ui.page;

import net.jan.moddirector.core.manage.select.InstallSelector;
import net.jan.moddirector.core.manage.select.SelectableInstallOption;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ModSelectionPage extends JPanel {
    public ModSelectionPage(InstallSelector selector) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Select mods to install", SwingConstants.CENTER);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 20));
        titleLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, titleLabel.getMinimumSize().height));
        add(titleLabel);

        // Create a new JPanel to hold the content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Add the content to the contentPanel
        selector.getSingleOptions().forEach(option -> setupSingleOption(contentPanel, option));
        selector.getGroupOptions().forEach((groupName, options) -> setupGroupOption(contentPanel, groupName, options));

        // Wrap the contentPanel with a JScrollPane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add the scrollPane to the main panel
        add(scrollPane);
    }

    private void setupSingleOption(JPanel contentPanel, SelectableInstallOption option) {
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));

        JCheckBox installCheckBox = new JCheckBox("Install");
        installCheckBox.setSelected(option.isSelected());
        installCheckBox.addItemListener(e -> option.setSelected(installCheckBox.isSelected()));
        optionPanel.add(installCheckBox);

        if (option.getDescription() != null) {
            optionPanel.add(new JLabel(asHtml(option.getDescription())));
        }

        optionPanel.setBorder(BorderFactory.createTitledBorder(option.getName()));

        contentPanel.add(optionPanel);
    }

    private void setupGroupOption(JPanel contentPanel, String groupName, List<SelectableInstallOption> options) {
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));

        ButtonGroup group = new ButtonGroup();

        for (SelectableInstallOption option : options) {
            JPanel optionPanel = new JPanel();
            optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));

            JRadioButton installRadioButton = new JRadioButton(option.getName());
            installRadioButton.setSelected(option.isSelected());
            installRadioButton.addItemListener(e -> option.setSelected(installRadioButton.isSelected()));
            group.add(installRadioButton);
            optionPanel.add(installRadioButton);

            if (option.getDescription() != null) {
                optionPanel.add(new JLabel(asHtml(option.getDescription())));
            }

            groupPanel.add(optionPanel);
        }

        groupPanel.setBorder(BorderFactory.createTitledBorder(groupName));

        contentPanel.add(groupPanel);
    }

    private String asHtml(String content) {
        return "<html>" + content + "</html>";
    }
}
