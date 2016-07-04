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

package com.ponysdk.core.ui.rich;

import java.time.Duration;

import com.ponysdk.core.server.concurrent.PScheduler;
import com.ponysdk.core.ui.basic.IsPWidget;
import com.ponysdk.core.ui.basic.PLabel;
import com.ponysdk.core.ui.basic.PPopupPanel;
import com.ponysdk.core.ui.basic.PSimplePanel;
import com.ponysdk.core.ui.basic.PWindow;
import com.ponysdk.core.ui.basic.event.PClickEvent;

public class PNotificationManager {

    public static int humanizedDuration = 5000;
    public static int warningDuration = 6000;
    public static int trayDuration = 6000;

    public static void notify(final String message, final Notification notification) {
        notify(PWindow.getMain().getID(), message, notification);
    }

    public static void notify(final int windowID, final String message, final Notification notification) {
        switch (notification) {
            case TRAY:
                showTrayNotification(windowID, new PLabel(message));
                break;
            case HUMANIZED:
                showHumanizedNotification(windowID, new PLabel(message));
                break;
            case WARNING_MESSAGE:
                showWarningNotification(windowID, new PLabel(message));
                break;
            case ERROR_MESSAGE:
                showErrorNotification(windowID, new PLabel(message));
                break;
            default:
                break;
        }
    }

    public static void notify(final int windowID, final IsPWidget content, final Notification notification) {
        switch (notification) {
            case TRAY:
                showTrayNotification(windowID, content);
                break;
            case HUMANIZED:
                showHumanizedNotification(windowID, content);
                break;
            case WARNING_MESSAGE:
                showWarningNotification(windowID, content);
                break;
            case ERROR_MESSAGE:
                showErrorNotification(windowID, content);
                break;
            default:
                break;
        }
    }

    public static void notify(final IsPWidget content, final Notification notification) {
        switch (notification) {
            case TRAY:
                showTrayNotification(content);
                break;
            case HUMANIZED:
                showHumanizedNotification(content);
                break;
            case WARNING_MESSAGE:
                showWarningNotification(content);
                break;
            case ERROR_MESSAGE:
                showErrorNotification(content);
                break;
            default:
                break;
        }
    }

    public static void showHumanizedNotification(final String message) {
        showHumanizedNotification(new PLabel(message));
    }

    public static void showWarningNotification(final String message) {
        showWarningNotification(new PLabel(message));
    }

    public static void showErrorNotification(final String message) {
        showErrorNotification(new PLabel(message));
    }

    public static void showTrayNotification(final String message) {
        showTrayNotification(new PLabel(message));
    }

    public static void showHumanizedNotification(final PWindow windowID, final String message) {
        showHumanizedNotification(windowID.getID(), new PLabel(message));
    }

    public static void showHumanizedNotification(final int windowID, final String message) {
        showHumanizedNotification(windowID, new PLabel(message));
    }

    public static void showWarningNotification(final PWindow window, final String message) {
        showWarningNotification(window.getID(), new PLabel(message));
    }

    public static void showWarningNotification(final int windowID, final String message) {
        showWarningNotification(windowID, new PLabel(message));
    }

    public static void showErrorNotification(final PWindow window, final String message) {
        showErrorNotification(window.getID(), new PLabel(message));
    }

    public static void showErrorNotification(final int windowID, final String message) {
        showErrorNotification(windowID, new PLabel(message));
    }

    public static void showTrayNotification(final PWindow window, final String message) {
        showTrayNotification(window.getID(), new PLabel(message));
    }

    public static void showTrayNotification(final int windowID, final String message) {
        showTrayNotification(windowID, new PLabel(message));
    }

    private static void showHumanizedNotification(final int windowID, final IsPWidget content) {
        final PPopupPanel popupPanel = new PPopupPanel(true);
        popupPanel.addStyleName("notification");
        popupPanel.addStyleName("humanized");
        popupPanel.setWidget(content);
        popupPanel.addDomHandler(event -> popupPanel.hide(), PClickEvent.TYPE);
        popupPanel.addStyleName("closing");
        popupPanel.center();

        addAutoCloseTimer(popupPanel, humanizedDuration);
    }

    private static void showHumanizedNotification(final IsPWidget content) {
        final PPopupPanel popupPanel = new PPopupPanel(true);
        popupPanel.addStyleName("notification");
        popupPanel.addStyleName("humanized");
        popupPanel.setWidget(content);
        popupPanel.addDomHandler(event -> popupPanel.hide(), PClickEvent.TYPE);
        popupPanel.addStyleName("closing");
        popupPanel.center();

        addAutoCloseTimer(popupPanel, humanizedDuration);
    }

    private static void showWarningNotification(final int windowID, final IsPWidget content) {
        final PPopupPanel popupPanel = new PPopupPanel(true);
        popupPanel.addStyleName("notification");
        popupPanel.addStyleName("warning");
        popupPanel.setWidget(content);
        popupPanel.addDomHandler(event -> popupPanel.hide(), PClickEvent.TYPE);

        popupPanel.center();
        addAutoCloseTimer(popupPanel, warningDuration);
    }

    private static void showWarningNotification(final IsPWidget content) {
        final PPopupPanel popupPanel = new PPopupPanel(true);
        popupPanel.addStyleName("notification");
        popupPanel.addStyleName("warning");
        popupPanel.setWidget(content);
        popupPanel.addDomHandler(event -> popupPanel.hide(), PClickEvent.TYPE);

        popupPanel.center();
        addAutoCloseTimer(popupPanel, warningDuration);
    }

    private static void showErrorNotification(final int windowID, final IsPWidget content) {
        final PPopupPanel popupPanel = new PPopupPanel(false);
        popupPanel.setGlassEnabled(false);
        popupPanel.addStyleName("notification");
        popupPanel.addStyleName("error");
        popupPanel.setWidget(content);
        popupPanel.addDomHandler(event -> popupPanel.hide(), PClickEvent.TYPE);

        popupPanel.center();
    }

    private static void showErrorNotification(final IsPWidget content) {
        final PPopupPanel popupPanel = new PPopupPanel(false);
        popupPanel.setGlassEnabled(false);
        popupPanel.addStyleName("notification");
        popupPanel.addStyleName("error");
        popupPanel.setWidget(content);
        popupPanel.addDomHandler(event -> popupPanel.hide(), PClickEvent.TYPE);

        popupPanel.center();
    }

    private static void showTrayNotification(final int windowID, final IsPWidget content) {
        final PSimplePanel div2 = new PSimplePanel();
        div2.setWidget(content);

        final PPopupPanel popupPanel = new PPopupPanel(true);
        popupPanel.addStyleName("notification");
        popupPanel.addStyleName("tray");
        popupPanel.setWidget(div2);
        displayAtBottomRight(popupPanel, "closing");
        addAutoCloseTimer(popupPanel, trayDuration);
    }

    private static void showTrayNotification(final IsPWidget content) {
        final PSimplePanel div2 = new PSimplePanel();
        div2.setWidget(content);

        final PPopupPanel popupPanel = new PPopupPanel(true);
        popupPanel.addStyleName("notification");
        popupPanel.addStyleName("tray");
        popupPanel.setWidget(div2);
        displayAtBottomRight(popupPanel, "closing");
        addAutoCloseTimer(popupPanel, trayDuration);
    }

    private static void displayAtBottomRight(final PPopupPanel popupPanel, final String closingAnimation) {
        popupPanel.setPopupPositionAndShow((offsetWidth, offsetHeight, windowWidth, windowHeight) -> {
            popupPanel.setPopupPosition(windowWidth - offsetWidth - 5, windowHeight - offsetHeight - 5);
            if (closingAnimation != null)
                popupPanel.addStyleName(closingAnimation);
        });
    }

    private static void addAutoCloseTimer(final PPopupPanel popupPanel, final int delayBeforeClosing) {
        PScheduler.schedule(popupPanel::hide, Duration.ofMillis(delayBeforeClosing));
    }

    public enum Notification {
        TRAY,
        HUMANIZED,
        WARNING_MESSAGE,
        ERROR_MESSAGE
    }
}
