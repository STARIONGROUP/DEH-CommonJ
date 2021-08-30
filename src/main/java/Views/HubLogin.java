/*
 * HubLogin.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
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
import ViewModels.Interfaces.IHubLoginViewModel;
import ViewModels.Interfaces.IViewModel;
import Views.Interfaces.IDialog;
import net.bytebuddy.asm.Advice.This;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
@SuppressWarnings("serial")
public class HubLogin extends JDialog implements IDialog<IHubLoginViewModel, Boolean>
{   
    /**
     * The current class log4J {@linkplain Logger}
     */
    private final Logger logger = LogManager.getLogger();
    
    /**
     * Backing field for {@linkplain GetDialogResult()}
     */
    private Boolean dialogResult;

    /**
     * This view attached {@linkplain IViewModel}
     */
    private IHubLoginViewModel dataContext;
    
    /**
     * UI Elements declarations
     */
    private final JPanel contentPanel = new JPanel();
    private JButton okButton;
    private JButton cancelButton;
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
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[]{220, 220, 0};
        gbl_contentPanel.rowHeights = new int[] {30, 0, 0, 0};
        gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
        contentPanel.setLayout(gbl_contentPanel);
        {
            JLabel lblNewLabel_3 = new JLabel("Connections");
            lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
            GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
            gbc_lblNewLabel_3.gridwidth = 2;
            gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
            gbc_lblNewLabel_3.gridx = 0;
            gbc_lblNewLabel_3.gridy = 0;
            contentPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
        }
        {
            JPanel panel = new JPanel();
            GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.gridwidth = 2;
            gbc_panel.insets = new Insets(0, 0, 0, 5);
            gbc_panel.fill = GridBagConstraints.BOTH;
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 2;
            contentPanel.add(panel, gbc_panel);
            GridBagLayout gbl_panel = new GridBagLayout();
            gbl_panel.columnWidths = new int[]{0, 0};
            gbl_panel.rowHeights = new int[]{0, 0, 0};
            gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
            gbl_panel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
            panel.setLayout(gbl_panel);
            {
                this.loginPanelContainer = new JPanel();
                GridBagConstraints gbc_loginPanelContainer = new GridBagConstraints();
                gbc_loginPanelContainer.insets = new Insets(0, 0, 5, 0);
                gbc_loginPanelContainer.fill = GridBagConstraints.BOTH;
                gbc_loginPanelContainer.gridx = 0;
                gbc_loginPanelContainer.gridy = 0;
                panel.add(this.loginPanelContainer, gbc_loginPanelContainer);
                GridBagLayout gbl_loginPanelContainer = new GridBagLayout();
                gbl_loginPanelContainer.columnWidths = new int[]{150, 0, 50, 0};
                gbl_loginPanelContainer.rowHeights = new int[]{50, 0, 0, 0, 0};
                gbl_loginPanelContainer.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
                gbl_loginPanelContainer.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
                this.loginPanelContainer.setLayout(gbl_loginPanelContainer);
                {
                    JLabel lblNewLabel_5 = new JLabel("Login");
                    lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 16));
                    GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
                    gbc_lblNewLabel_5.fill = GridBagConstraints.VERTICAL;
                    gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
                    gbc_lblNewLabel_5.gridx = 1;
                    gbc_lblNewLabel_5.gridy = 0;
                    this.loginPanelContainer.add(lblNewLabel_5, gbc_lblNewLabel_5);
                }
                {
                    JLabel lblNewLabel_4 = new JLabel("Address");
                    GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
                    gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
                    gbc_lblNewLabel_4.insets = new Insets(10, 10, 10, 10);
                    gbc_lblNewLabel_4.gridx = 0;
                    gbc_lblNewLabel_4.gridy = 1;
                    this.loginPanelContainer.add(lblNewLabel_4, gbc_lblNewLabel_4);
                }
                {
                    this.addressComboBox = new JComboBox<String>();
                    this.addressComboBox.setToolTipText("Select the address of the 10-25 server to connect to");
                    this.addressComboBox.setEditable(true);
                    GridBagConstraints gbc_addressComboBox = new GridBagConstraints();
                    gbc_addressComboBox.fill = GridBagConstraints.HORIZONTAL;
                    gbc_addressComboBox.insets = new Insets(10, 10, 10, 10);
                    gbc_addressComboBox.gridx = 1;
                    gbc_addressComboBox.gridy = 1;
                    this.loginPanelContainer.add(this.addressComboBox, gbc_addressComboBox);
                }
                {
                    JButton btnNewButton = new JButton("\uD83D\uDCBE");
                    GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
                    gbc_btnNewButton.fill = GridBagConstraints.BOTH;
                    gbc_btnNewButton.insets = new Insets(10, 10, 10, 10);
                    gbc_btnNewButton.gridx = 2;
                    gbc_btnNewButton.gridy = 1;
                    this.loginPanelContainer.add(btnNewButton, gbc_btnNewButton);
                }
                {
                    JLabel lblNewLabel_4 = new JLabel("Login");
                    GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
                    gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
                    gbc_lblNewLabel_4.insets = new Insets(10, 10, 10, 10);
                    gbc_lblNewLabel_4.gridx = 0;
                    gbc_lblNewLabel_4.gridy = 2;
                    this.loginPanelContainer.add(lblNewLabel_4, gbc_lblNewLabel_4);
                }
                {
                    this.loginField = new JTextField();
                    this.loginField.setToolTipText("Enter your COMET login");
                    this.loginField.setColumns(10);
                    GridBagConstraints gbc_loginField = new GridBagConstraints();
                    gbc_loginField.fill = GridBagConstraints.HORIZONTAL;
                    gbc_loginField.gridwidth = 2;
                    gbc_loginField.insets = new Insets(0, 10, 10, 10);
                    gbc_loginField.gridx = 1;
                    gbc_loginField.gridy = 2;
                    this.loginPanelContainer.add(this.loginField, gbc_loginField);
                }
                {
                    JLabel lblNewLabel_4 = new JLabel("Password");
                    GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
                    gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
                    gbc_lblNewLabel_4.insets = new Insets(10, 10, 10, 10);
                    gbc_lblNewLabel_4.gridx = 0;
                    gbc_lblNewLabel_4.gridy = 3;
                    this.loginPanelContainer.add(lblNewLabel_4, gbc_lblNewLabel_4);
                }
                {
                    this.passwordField = new JPasswordField();
                    this.passwordField.setToolTipText("Enter your COMET password");
                    GridBagConstraints gbc_passwordField = new GridBagConstraints();
                    gbc_passwordField.gridwidth = 2;
                    gbc_passwordField.insets = new Insets(0, 10, 10, 10);
                    gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
                    gbc_passwordField.gridx = 1;
                    gbc_passwordField.gridy = 3;
                    this.loginPanelContainer.add(this.passwordField, gbc_passwordField);
                }
            }
            {
                this.sessionPanelContainer = new JPanel();
                GridBagConstraints gbc_Session = new GridBagConstraints();
                gbc_Session.fill = GridBagConstraints.BOTH;
                gbc_Session.gridx = 0;
                gbc_Session.gridy = 1;
                panel.add(this.sessionPanelContainer, gbc_Session);
                GridBagLayout gbl_Session = new GridBagLayout();
                gbl_Session.columnWidths = new int[] {150, 0, 50};
                gbl_Session.rowHeights = new int[]{50, 0, 0, 0, 0};
                gbl_Session.columnWeights = new double[]{0.0, 1.0, 0.0};
                gbl_Session.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
                this.sessionPanelContainer.setEnabled(false);
                this.sessionPanelContainer.setLayout(gbl_Session);
                {
                    JLabel lblNewLabel_5 = new JLabel("Session");
                    lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 16));
                    GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
                    gbc_lblNewLabel_5.fill = GridBagConstraints.VERTICAL;
                    gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
                    gbc_lblNewLabel_5.gridx = 1;
                    gbc_lblNewLabel_5.gridy = 0;
                    sessionPanelContainer.add(lblNewLabel_5, gbc_lblNewLabel_5);
                }
                {
                    JLabel lblNewLabel = new JLabel("Engineering Model");
                    GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
                    gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
                    gbc_lblNewLabel.insets = new Insets(10, 10, 10, 10);
                    gbc_lblNewLabel.gridx = 0;
                    gbc_lblNewLabel.gridy = 1;
                    this.sessionPanelContainer.add(lblNewLabel, gbc_lblNewLabel);
                }
                {
                    this.engineeringModelSetupComboBox = new JComboBox<String>();
                    GridBagConstraints gbc_comboBox = new GridBagConstraints();
                    gbc_comboBox.gridwidth = 2;
                    gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
                    gbc_comboBox.insets = new Insets(10, 10, 10, 10);
                    gbc_comboBox.gridx = 1;
                    gbc_comboBox.gridy = 1;
                    this.sessionPanelContainer.add(this.engineeringModelSetupComboBox, gbc_comboBox);
                }
                {
                    JLabel lblNewLabel_1 = new JLabel("Iteration");
                    GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
                    gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
                    gbc_lblNewLabel_1.insets = new Insets(10, 10, 10, 10);
                    gbc_lblNewLabel_1.gridx = 0;
                    gbc_lblNewLabel_1.gridy = 2;
                    this.sessionPanelContainer.add(lblNewLabel_1, gbc_lblNewLabel_1);
                }
                {
                    this.iterationComboBox = new JComboBox<String>();
                    GridBagConstraints gbc_comboBox = new GridBagConstraints();
                    gbc_comboBox.gridwidth = 2;
                    gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
                    gbc_comboBox.insets = new Insets(10, 10, 10, 10);
                    gbc_comboBox.gridx = 1;
                    gbc_comboBox.gridy = 2;
                    this.sessionPanelContainer.add(this.iterationComboBox, gbc_comboBox);
                }
                {
                    JLabel lblNewLabel_2 = new JLabel("Domain of expertise");
                    GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
                    gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
                    gbc_lblNewLabel_2.insets = new Insets(10, 10, 10, 10);
                    gbc_lblNewLabel_2.gridx = 0;
                    gbc_lblNewLabel_2.gridy = 3;
                    this.sessionPanelContainer.add(lblNewLabel_2, gbc_lblNewLabel_2);
                }
                {
                    this.domainComboBox = new JComboBox<String>();
                    GridBagConstraints gbc_comboBox = new GridBagConstraints();
                    gbc_comboBox.gridwidth = 2;
                    gbc_comboBox.insets = new Insets(10, 10, 10, 10);
                    gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
                    gbc_comboBox.gridx = 1;
                    gbc_comboBox.gridy = 3;
                    this.sessionPanelContainer.add(this.domainComboBox, gbc_comboBox);
                }

                this.sessionPanelContainer.setVisible(false);
            }
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                this.okButton = new JButton("Next");
                okButton.setBounds(50, 125, 150, 50);
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton); 
            }
            {
                this.cancelButton = new JButton("Cancel");
                this.cancelButton.setActionCommand("Cancel");
                buttonPane.add(this.cancelButton);
            }
        }
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
     * 
     * @param <code>viewModel</code> the view model to bind
     */
    public void Bind()
    {
        this.addressComboBox.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                dataContext.WhenAddressComboxIsEdited(addressComboBox, e);
            }
        });
        
        this.dataContext.GetAddresses().stream().forEach(x -> this.addressComboBox.addItem(x));
        
        this.engineeringModelSetupComboBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                iterationComboBox.removeAllItems();
                domainComboBox.removeAllItems();
                String modelSetup = (String)engineeringModelSetupComboBox.getSelectedItem();
                
                dataContext.GetIterations(modelSetup)
                    .forEach(x -> iterationComboBox.addItem(x));
                
                Pair<Stream<String>, String> domains = dataContext.GetDomainOfExpertise(modelSetup);
                
                domains.getLeft()
                    .forEach(x -> domainComboBox.addItem(x));
                
                domainComboBox.setSelectedItem(domains.getRight());
            }
        });
        
        this.cancelButton.addActionListener(new ActionListener() 
        {                    
            public void actionPerformed(ActionEvent e) 
            {
                CloseDialog(false);
            }
        });
        
        
        this.okButton.addActionListener(new ActionListener() 
        {                    
            public void actionPerformed(ActionEvent e) 
            {
                if(!dataContext.GetIsLoginSuccessful())
                {
                    LoginAction();
                }
                else
                {
                    IterationSelectionAction();
                }
            }
        });
    }
    
    /**
     * Sets the session data and closes this dialog
     */
    private void IterationSelectionAction()
    {
        try
        {
            String selectedEngineeringModelSetup = (String)this.engineeringModelSetupComboBox.getSelectedItem();
            String selectedIterationSetup = (String)this.iterationComboBox.getSelectedItem();
            String selectedDomainOfExpertise = (String)this.domainComboBox.getSelectedItem();
                    
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
            JOptionPane.showMessageDialog(this.sessionPanelContainer, MessageFormat.format("An error occured: {0}. Have a look at the Cameo/MagicDraw log for detail.", exception));
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
                    JOptionPane.showMessageDialog(this.loginPanelContainer, "An error occured while trying to login. Have a look at the Cameo/MagicDraw log for detail.");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this.loginPanelContainer, "Please select an valid server address to continue");
            }
        }
        catch (Exception exception)
        {
            this.logger.catching(exception);
            JOptionPane.showMessageDialog(this.sessionPanelContainer, MessageFormat.format("An error occured: {0}. Have a look at the Cameo/MagicDraw log for detail.", exception));
        }
    }
    
    /**
     * Sets the DataContext
     * 
     * @param viewModel the {@link IViewModel} to assign
     */
    public void SetDataContext(IViewModel viewModel)
    {
        this.dataContext = (IHubLoginViewModel) viewModel;
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
