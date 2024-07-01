/*
 * ExchangeHistoryRenderDataProvider.java
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
package ViewModels.ExchangeHistory;

import javax.swing.Icon;

import org.netbeans.swing.outline.RenderDataProvider;

import Renderers.DefaultRenderDataProvider;
import Utils.ImageLoader.ImageLoader;
import ViewModels.ExchangeHistory.Rows.ExchangeHistoryEntryRowViewModel;
import ViewModels.ExchangeHistory.Rows.ExchangeHistoryTimeStampRowViewModel;
import Views.ExchangeHistory.ExchangeHistoryDialog;

/**
 * The {@linkplain ExchangeHistoryRenderDataProvider} is the
 * {@linkplain RenderDataProvider} for {@linkplain ExchangeHistoryDialog} object
 * browser
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
public class ExchangeHistoryRenderDataProvider extends DefaultRenderDataProvider
{
	/**
	 * Gets the specified row view model node name
	 * 
	 * @param rowViewModel the row view model to get the name from
	 * @return a {@linkplain String}
	 */
	@Override
	public String getDisplayName(Object rowViewModel)
	{
		if (rowViewModel instanceof ExchangeHistoryTimeStampRowViewModel)
		{
			return String.format("<html><b>%s</b></html>",
					((ExchangeHistoryTimeStampRowViewModel) rowViewModel).GetName());
		}
		else if (rowViewModel instanceof ExchangeHistoryEntryRowViewModel && ((ExchangeHistoryEntryRowViewModel)rowViewModel).GetNodeName() != null)
		{
		    return ((ExchangeHistoryEntryRowViewModel)rowViewModel).GetNodeName();
		}

		return "";
	}

	/**
	 * Gets the icon to be used for rendering this node. Returns null if the row
	 * corresponds to a {@linkplain ExchangeHistoryTimeStampRowViewModel}
	 * 
	 * @param rowViewModel the row view model
	 * @return a {@linkplain Icon}
	 */
	@Override
	public Icon getIcon(Object rowViewModel)
	{
		if (rowViewModel instanceof ExchangeHistoryTimeStampRowViewModel)
		{
			return ImageLoader.GetIcon();
		}

		return null;
	}
}
