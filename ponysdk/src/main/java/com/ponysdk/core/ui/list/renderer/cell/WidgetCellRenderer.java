/*
 * Copyright (c) 2011 PonySDK
 *  Owners:
 *  Luciano Broussal  <luciano.broussal AT gmail.com>
 *	Mathieu Barbier   <mathieu.barbier AT gmail.com>
 *	Nicolas Ciaravola <nicolas.ciaravola.pro AT gmail.com>
 *
 *  WebSite:
 *  http://code.google.com/p/pony-sdk/
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ponysdk.core.ui.list.renderer.cell;

import javax.validation.constraints.NotNull;

import com.ponysdk.core.ui.basic.Element;
import com.ponysdk.core.ui.basic.PWidget;
import com.ponysdk.core.ui.place.Place;

public abstract class WidgetCellRenderer<D> implements CellRenderer<D, PWidget> {

    private static final String DASH = "-";

    protected String nullDisplay = DASH;

    public WidgetCellRenderer() {
    }

    public WidgetCellRenderer(final Place place, final String nullDisplay) {
        this.nullDisplay = nullDisplay;
    }

    @Override
    public PWidget render(final int row, final D rawValue) {
        final String value = rawValue != null ? getValue(rawValue) : null;
        return value != null ? render0(row, rawValue) : Element.newPLabel(nullDisplay);
    }

    protected abstract PWidget render0(int row, D rawValue);

    public String getValue(@NotNull final D value) {
        return value.toString();
    }

    public void setNullDisplay(final String nullDisPlay) {
        this.nullDisplay = nullDisPlay;
    }

}
