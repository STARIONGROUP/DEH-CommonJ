/*
 * HubLogin.java
 *
 * Copyright (c) 2020-2024 Starion Group S.A.
 *
 * Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski 
 *
 * This file is part of DEH-CommonJ
 *
 * The DEH-CommonJ is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * The DEH-CommonJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package Views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Utils.ImageLoader.ImageLoader;
import ViewModels.Interfaces.IHubLoginViewModel;
import ViewModels.Interfaces.IViewModel;
import Views.Interfaces.IDialog;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.stream.Stream;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

/**
 * The {@linkplain HubLogin} is the dialog view to allow login to a 10-25 server
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
@SuppressWarnings("serial")
public class HubLogin extends JDialog implements IDialog<IHubLoginViewModel, Boolean>
{
	/**
	 * The name of the font 
	 */
    private static final String FONTNAME = "Tahoma";

	/**
     * The current class log4J {@linkplain Logger}
     */
    private final transient Logger logger = LogManager.getLogger();
    
    /**
     * Backing field for {@linkplain #GetDialogResult()}
     */
    private Boolean dialogResult;

    /**
     * This view attached {@linkplain IViewModel}
     */
    private transient IHubLoginViewModel dataContext;
    
    /**
     * UI Elements declarations
     */
    private final JPanel contentPanel = new JPanel();
    private JButton okButton;
    private JButton cancelButton;
    private JButton saveUriButton;
    private JTextField loginField;
    private JPanel sessionPanelContainer;
    private JPanel loginPanelContainer;
    private JComboBox<String> addressComboBox;
    private JPasswordField passwordField;
    private JComboBox<String> engineeringModelSetupComboBox;
    private JComboBox<String> iterationComboBox;
    private JComboBox<String> domainComboBox;
         
    /**
     * Initializes a new {@link HubLogin}
     */
    public HubLogin()
    {
        setTitle("Connections");
        setType(Type.POPUP);
        setModal(true);
        setBounds(100, 100, 549, 504);
        setMinimumSize(getSize());
        this.setIconImage(ImageLoader.GetIcon("icon16.png").getImage());
        this.getContentPane().setLayout(new BorderLayout());
        this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.getContentPane().add(this.contentPanel, BorderLayout.CENTER);
        GridBagLayout gblContentPanel = new GridBagLayout();
        gblContentPanel.columnWidths = new int[]{220, 220, 0};
        gblContentPanel.rowHeights = new int[] {30, 0, 0, 0};
        gblContentPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gblContentPanel.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
        contentPanel.setLayout(gblContentPanel);
        JLabel lblConnections = new JLabel("Connections");
        lblConnections.setFont(new Font(FONTNAME, Font.PLAIN, 16));
        GridBagConstraints gbcLblConnections = new GridBagConstraints();
        gbcLblConnections.gridwidth = 2;
        gbcLblConnections.insets = new Insets(0, 0, 5, 0);
        gbcLblConnections.gridx = 0;
        gbcLblConnections.gridy = 0;
        contentPanel.add(lblConnections, gbcLblConnections);
        JPanel panel = new JPanel();
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.gridwidth = 2;
        gbcPanel.insets = new Insets(0, 0, 0, 5);
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 2;
        contentPanel.add(panel, gbcPanel);
        GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWidths = new int[]{0, 0};
        gblPanel.rowHeights = new int[]{0, 0, 0};
        gblPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gblPanel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        panel.setLayout(gblPanel);
        this.loginPanelContainer = new JPanel();
        GridBagConstraints gbcLoginPanelContainer = new GridBagConstraints();
        gbcLoginPanelContainer.insets = new Insets(0, 0, 5, 0);
        gbcLoginPanelContainer.fill = GridBagConstraints.BOTH;
        gbcLoginPanelContainer.gridx = 0;
        gbcLoginPanelContainer.gridy = 0;
        panel.add(this.loginPanelContainer, gbcLoginPanelContainer);
        GridBagLayout gblLoginPanelContainer = new GridBagLayout();
        gblLoginPanelContainer.columnWidths = new int[]{150, 0, 50, 0};
        gblLoginPanelContainer.rowHeights = new int[]{50, 0, 0, 0, 0};
        gblLoginPanelContainer.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gblLoginPanelContainer.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        this.loginPanelContainer.setLayout(gblLoginPanelContainer);
        JLabel lblLogin = new JLabel("Login");
        lblLogin.setFont(new Font(FONTNAME, Font.BOLD, 16));
        GridBagConstraints gbcLblLogin = new GridBagConstraints();
        gbcLblLogin.fill = GridBagConstraints.VERTICAL;
        gbcLblLogin.insets = new Insets(0, 0, 5, 5);
        gbcLblLogin.gridx = 1;
        gbcLblLogin.gridy = 0;
        this.loginPanelContainer.add(lblLogin, gbcLblLogin);
        JLabel lblAddress = new JLabel("Address");
        GridBagConstraints gbclblAddress = new GridBagConstraints();
        gbclblAddress.anchor = GridBagConstraints.EAST;
        gbclblAddress.insets = new Insets(10, 10, 10, 10);
        gbclblAddress.gridx = 0;
        gbclblAddress.gridy = 1;
        this.loginPanelContainer.add(lblAddress, gbclblAddress);
        this.addressComboBox = new JComboBox<>();
        this.addressComboBox.setToolTipText("Select the address of the 10-25 server to connect to");
        this.addressComboBox.setEditable(true);
        GridBagConstraints gbcAddressComboBox = new GridBagConstraints();
        gbcAddressComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbcAddressComboBox.insets = new Insets(10, 10, 10, 10);
        gbcAddressComboBox.gridx = 1;
        gbcAddressComboBox.gridy = 1;
        this.loginPanelContainer.add(this.addressComboBox, gbcAddressComboBox);
        this.saveUriButton = new JButton("\uD83D\uDCBE");
        this.saveUriButton.setToolTipText("Save the current selected address");
        GridBagConstraints gbcSaveUriButton = new GridBagConstraints();
        gbcSaveUriButton.fill = GridBagConstraints.BOTH;
        gbcSaveUriButton.insets = new Insets(10, 10, 10, 10);
        gbcSaveUriButton.gridx = 2;
        gbcSaveUriButton.gridy = 1;
        this.loginPanelContainer.add(this.saveUriButton, gbcSaveUriButton);
        JLabel loginLabel = new JLabel("Login");
        GridBagConstraints loginLabelConstraints = new GridBagConstraints();
        loginLabelConstraints.anchor = GridBagConstraints.EAST;
        loginLabelConstraints.insets = new Insets(10, 10, 10, 10);
        loginLabelConstraints.gridx = 0;
        loginLabelConstraints.gridy = 2;
        this.loginPanelContainer.add(loginLabel, loginLabelConstraints);
        this.loginField = new JTextField();
        this.loginField.setToolTipText("Enter your COMET login");
        this.loginField.setColumns(10);
        GridBagConstraints gbcLoginField = new GridBagConstraints();
        gbcLoginField.fill = GridBagConstraints.HORIZONTAL;
        gbcLoginField.gridwidth = 2;
        gbcLoginField.insets = new Insets(0, 10, 10, 10);
        gbcLoginField.gridx = 1;
        gbcLoginField.gridy = 2;
        this.loginPanelContainer.add(this.loginField, gbcLoginField);
        JLabel passwordLabel = new JLabel("Password");
        GridBagConstraints passwordLabelConstraints = new GridBagConstraints();
        passwordLabelConstraints.anchor = GridBagConstraints.EAST;
        passwordLabelConstraints.insets = new Insets(10, 10, 10, 10);
        passwordLabelConstraints.gridx = 0;
        passwordLabelConstraints.gridy = 3;
        this.loginPanelContainer.add(passwordLabel, passwordLabelConstraints);
        this.passwordField = new JPasswordField();
        this.passwordField.setToolTipText("Enter your COMET password");
        GridBagConstraints gbcPasswordField = new GridBagConstraints();
        gbcPasswordField.gridwidth = 2;
        gbcPasswordField.insets = new Insets(0, 10, 10, 10);
        gbcPasswordField.fill = GridBagConstraints.HORIZONTAL;
        gbcPasswordField.gridx = 1;
        gbcPasswordField.gridy = 3;
        this.loginPanelContainer.add(this.passwordField, gbcPasswordField);
        this.sessionPanelContainer = new JPanel();
        GridBagConstraints gbcSession = new GridBagConstraints();
        gbcSession.fill = GridBagConstraints.BOTH;
        gbcSession.gridx = 0;
        gbcSession.gridy = 1;
        panel.add(this.sessionPanelContainer, gbcSession);
        GridBagLayout gblSession = new GridBagLayout();
        gblSession.columnWidths = new int[] {150, 0, 50};
        gblSession.rowHeights = new int[]{50, 0, 0, 0, 0};
        gblSession.columnWeights = new double[]{0.0, 1.0, 0.0};
        gblSession.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        this.sessionPanelContainer.setEnabled(false);
        this.sessionPanelContainer.setLayout(gblSession);
        JLabel sessionLabel = new JLabel("Session");
        sessionLabel.setFont(new Font(FONTNAME, Font.BOLD, 16));
        GridBagConstraints sessionLabelConstraints = new GridBagConstraints();
        sessionLabelConstraints.fill = GridBagConstraints.VERTICAL;
        sessionLabelConstraints.insets = new Insets(0, 0, 5, 5);
        sessionLabelConstraints.gridx = 1;
        sessionLabelConstraints.gridy = 0;
        sessionPanelContainer.add(sessionLabel, sessionLabelConstraints);
        JLabel lblNewLabel = new JLabel("Engineering Model");
        GridBagConstraints gbcLblNewLabel = new GridBagConstraints();
        gbcLblNewLabel.anchor = GridBagConstraints.EAST;
        gbcLblNewLabel.insets = new Insets(10, 10, 10, 10);
        gbcLblNewLabel.gridx = 0;
        gbcLblNewLabel.gridy = 1;
        this.sessionPanelContainer.add(lblNewLabel, gbcLblNewLabel);
        this.engineeringModelSetupComboBox = new JComboBox<>();
        GridBagConstraints gbcComboBox = new GridBagConstraints();
        gbcComboBox.gridwidth = 2;
        gbcComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbcComboBox.insets = new Insets(10, 10, 10, 10);
        gbcComboBox.gridx = 1;
        gbcComboBox.gridy = 1;
        this.sessionPanelContainer.add(this.engineeringModelSetupComboBox, gbcComboBox);
        JLabel lblIteration = new JLabel("Iteration");
        GridBagConstraints gbcLblNewLabelEast = new GridBagConstraints();
        gbcLblNewLabelEast.anchor = GridBagConstraints.EAST;
        gbcLblNewLabelEast.insets = new Insets(10, 10, 10, 10);
        gbcLblNewLabelEast.gridx = 0;
        gbcLblNewLabelEast.gridy = 2;
        this.sessionPanelContainer.add(lblIteration, gbcLblNewLabelEast);
        this.iterationComboBox = new JComboBox<>();
        GridBagConstraints iterationComboBoxConstraints = new GridBagConstraints();
        iterationComboBoxConstraints.gridwidth = 2;
        iterationComboBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
        iterationComboBoxConstraints.insets = new Insets(10, 10, 10, 10);
        iterationComboBoxConstraints.gridx = 1;
        iterationComboBoxConstraints.gridy = 2;
        this.sessionPanelContainer.add(this.iterationComboBox, iterationComboBoxConstraints);
        JLabel lblDomainOfExpertise = new JLabel("Domain of expertise");
        GridBagConstraints gbcLblNewLabel2 = new GridBagConstraints();
        gbcLblNewLabel2.anchor = GridBagConstraints.EAST;
        gbcLblNewLabel2.insets = new Insets(10, 10, 10, 10);
        gbcLblNewLabel2.gridx = 0;
        gbcLblNewLabel2.gridy = 3;
        this.sessionPanelContainer.add(lblDomainOfExpertise, gbcLblNewLabel2);
        this.domainComboBox = new JComboBox<>();
        GridBagConstraints gbcDomainComboBoxConstraints = new GridBagConstraints();
        gbcDomainComboBoxConstraints.gridwidth = 2;
        gbcDomainComboBoxConstraints.insets = new Insets(10, 10, 10, 10);
        gbcDomainComboBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
        gbcDomainComboBoxConstraints.gridx = 1;
        gbcDomainComboBoxConstraints.gridy = 3;
        this.sessionPanelContainer.add(this.domainComboBox, gbcDomainComboBoxConstraints);

        this.sessionPanelContainer.setVisible(false);
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
        this.okButton = new JButton("Next");
        okButton.setToolTipText("Continue with the current login / Session information");
        okButton.setBounds(50, 125, 150, 50);
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
        this.cancelButton = new JButton("Cancel");
        cancelButton.setToolTipText("Close this dialog and abort the connection");
        this.cancelButton.setActionCommand("Cancel");
        buttonPane.add(this.cancelButton);
    }
    
    private URI ParseAddress()
    {
        Object address = addressComboBox.getSelectedItem();
       
        try
        {
            return new URI((String)address);
        }
        catch (URISyntaxException e)
        {
           JOptionPane.showMessageDialog(addressComboBox, MessageFormat.format("{0} is not a valid URI", address));
           return null;
        }
    }
    
    /**
     * Binds the <code>TViewModel viewModel</code> to the implementing view
     */
    public void Bind()
    {
        this.addressComboBox.addActionListener(e -> dataContext.WhenAddressComboxIsEdited(addressComboBox, e));
        
        this.saveUriButton.addActionListener(e -> dataContext.DoSaveTheCurrentSelectedUri((String)addressComboBox.getSelectedItem()));
        
        this.dataContext.GetAddresses().stream().forEach(x -> this.addressComboBox.addItem(x));
        
        this.engineeringModelSetupComboBox.addActionListener(e -> 
        {
            iterationComboBox.removeAllItems();
            domainComboBox.removeAllItems();
            String modelSetup = (String)engineeringModelSetupComboBox.getSelectedItem();
            
            dataContext.GetIterations(modelSetup)
                .forEach(x1 -> iterationComboBox.addItem(x1));
            
            Pair<Stream<String>, String> domains = dataContext.GetDomainOfExpertise(modelSetup);
            
            domains.getLeft()
                .forEach(x2 -> domainComboBox.addItem(x2));
            
            domainComboBox.setSelectedItem(domains.getRight());
        });
        
        this.cancelButton.addActionListener(e -> CloseDialog(false));

        this.okButton.addActionListener(e -> 
        {
            if(!dataContext.GetIsLoginSuccessful())
            {
                LoginAction();
            }
            else
            {
                IterationSelectionAction();
            }
        });
    }
    
    /**
     * Sets the session data and closes this dialog
     */
    private void IterationSelectionAction()
    {
        String selectedEngineeringModelSetup = (String)this.engineeringModelSetupComboBox.getSelectedItem();
        String selectedIterationSetup = (String)this.iterationComboBox.getSelectedItem();
        String selectedDomainOfExpertise = (String)this.domainComboBox.getSelectedItem();
        
        try
        {                    
            if(selectedEngineeringModelSetup == null || selectedIterationSetup == null || selectedDomainOfExpertise == null)
            {
                JOptionPane.showMessageDialog(this.sessionPanelContainer, "Please select an Engineering Model, an Iteration and a Domain in order to continue");
            }
            
            if(this.dataContext.OpenIteration(selectedEngineeringModelSetup, selectedIterationSetup, selectedDomainOfExpertise))
            {
                this.CloseDialog(true);
            }
            
        } 
        catch (Exception exception)
        {
            logger.catching(exception);
            JOptionPane.showMessageDialog(this.sessionPanelContainer, String.format("An error occured whil trying to open the %s from %s with %s",
                    selectedIterationSetup, selectedEngineeringModelSetup, selectedDomainOfExpertise));
        }
    }
    
    /**
     * Authenticate the user and fills the {@link engineeringModelSetupComboBox} items collection
     */
    private void LoginAction()
    {
        try
        {
            URI address = ParseAddress();
    
            if(address != null)
            {
                this.dataContext.Login(this.loginField.getText(), String.valueOf(this.passwordField.getPassword()), address);
                
                if(this.dataContext.GetIsLoginSuccessful())
                {
                    this.engineeringModelSetupComboBox.removeAllItems();
                    this.loginPanelContainer.setVisible(false);
                    this.sessionPanelContainer.setVisible(true);
                    
                    this.dataContext.GetEngineeringModels()
                        .forEachOrdered(x -> this.engineeringModelSetupComboBox.addItem(x));
                }
                else
                {
                    JOptionPane.showMessageDialog(this.loginPanelContainer, "Could not login with the provided credentials, verify if the entered information is correct and the server is available.");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this.loginPanelContainer, "Please select a valid server address to continue");
            }
        }
        catch (Exception exception)
        {
            this.logger.catching(exception);
            JOptionPane.showMessageDialog(this.sessionPanelContainer, "Could not login with the provided credentials, verify if the entered information is correct and the server is available.");
        }
    }
    
    /**
     * Sets the DataContext
     * 
     * @param viewModel the {@link IViewModel} to assign
     */
    public void SetDataContext(IHubLoginViewModel viewModel)
    {
        this.dataContext = viewModel;
        this.Bind();
    }
    
    /**
    * Gets the DataContext
    * 
    * @return An {@link IViewModel}
    */
    @Override
    public IHubLoginViewModel GetDataContext()
    {
        return this.dataContext;
    }

    /**
     * Shows the dialog and return the result
     * 
     * @return a {@linkplain TResult}
     */
    @Override
    public Boolean ShowDialog()
    {
        this.setVisible(true);
        return this.dialogResult;
    }
    
    /**
     * Closes the dialog and sets the {@link dialogResult}
     * 
     * @param result the {@linkplain TResult} to set
     */
    @Override
    public void CloseDialog(Boolean result)
    {
        this.dialogResult = result;
        setVisible(false);
        dispose();
    }
    
    /**
     * Gets the {@linkplain dialogResult}
     * 
     * @return a {@linkplain Boolean}
     */
    public Boolean GetDialogResult()
    {
        return this.dialogResult;
    }
}
