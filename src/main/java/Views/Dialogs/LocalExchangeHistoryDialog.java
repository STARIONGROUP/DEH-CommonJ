/*
 * LocalExchangeHistoryDialog.java
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
package Views.Dialogs;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Utils.ImageLoader.ImageLoader;
import java.awt.GridLayout;
import Views.ObjectBrowser.ObjectBrowser;

/**
 * The {@linkplain LocalExchangeHistoryDialog} 
 */
@SuppressWarnings("serial")
public class LocalExchangeHistoryDialog extends JDialog
{
    /**
     * View components declarations
     */
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    LocalExchangeHistoryDialog frame = new LocalExchangeHistoryDialog();
                    frame.setVisible(true);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Initializes a new {@linkplain LocalExchangeHistoryDialog}
     */
    public LocalExchangeHistoryDialog()
    {
        this.Initialize();
    }
    
    /**
     * Initializes this view components
     */
    private void Initialize()
    {
        this.setTitle("Local Exchange History");
        this.setModal(true);
        this.setIconImage(ImageLoader.GetIcon().getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 500, 700);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(1, 0, 0, 0));
        
        ObjectBrowser objectBrowser = new ObjectBrowser();
        contentPane.add(objectBrowser);
    }
}
