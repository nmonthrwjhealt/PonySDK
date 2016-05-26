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

package com.ponysdk.ui.terminal.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;
import com.google.gwt.user.client.ui.Widget;
import com.ponysdk.ui.terminal.UIService;
import com.ponysdk.ui.terminal.model.BinaryModel;
import com.ponysdk.ui.terminal.model.ReaderBuffer;
import com.ponysdk.ui.terminal.model.ServerToClientModel;

public class PTDockLayoutPanel extends PTComplexPanel<DockLayoutPanel> {

    private UIService uiService;
    private Unit unit;

    @Override
    public void create(final ReaderBuffer buffer, final int objectId, final UIService uiService) {
        // ServerToClientModel.UNIT
        unit = Unit.values()[buffer.getBinaryModel().getByteValue()];

        super.create(buffer, objectId, uiService);

        this.uiService = uiService;
    }

    @Override
    protected DockLayoutPanel createUIObject() {
        return new DockLayoutPanel(unit);
    }

    @Override
    public boolean update(final ReaderBuffer buffer, final BinaryModel binaryModel) {
        if (ServerToClientModel.WIDGET_SIZE.equals(binaryModel.getModel())) {
            final double newSize = binaryModel.getDoubleValue();
            // ServerToClientModel.WIDGET_ID
            final Widget w = asWidget(buffer.getBinaryModel().getIntValue(), uiService);
            uiObject.setWidgetSize(w, newSize);
            return true;
        }
        if (ServerToClientModel.WIDGET_HIDDEN.equals(binaryModel.getModel())) {
            final boolean hidden = binaryModel.getBooleanValue();
            // ServerToClientModel.WIDGET_ID
            final Widget w = asWidget(buffer.getBinaryModel().getIntValue(), uiService);
            uiObject.setWidgetHidden(w, hidden);
            return true;
        }
        if (ServerToClientModel.ANIMATE.equals(binaryModel.getModel())) {
            uiObject.animate(binaryModel.getIntValue());
            return true;
        }
        return super.update(buffer, binaryModel);
    }

    @Override
    public void add(final ReaderBuffer buffer, final PTObject ptObject) {
        final Widget w = asWidget(ptObject);
        // ServerToClientModel.DIRECTION
        final Direction direction = Direction.values()[buffer.getBinaryModel().getByteValue()];
        // ServerToClientModel.SIZE
        final double size = buffer.getBinaryModel().getDoubleValue();

        switch (direction) {
            case CENTER: {
                uiObject.add(w);
                break;
            }
            case NORTH: {
                uiObject.addNorth(w, size);
                break;
            }
            case SOUTH: {
                uiObject.addSouth(w, size);
                break;
            }
            case EAST: {
                uiObject.addEast(w, size);
                break;
            }
            case WEST: {
                uiObject.addWest(w, size);
                break;
            }
            case LINE_START: {
                uiObject.addLineStart(w, size);
                break;
            }
            case LINE_END: {
                uiObject.addLineEnd(w, size);
                break;
            }
        }
    }

}