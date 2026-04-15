package com.marketplace.admin.view.adminaction;

import com.marketplace.admin.entity.AdminAction;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DefaultMainViewParent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "admin-actions", layout = DefaultMainViewParent.class)
@ViewController(id = "admin_AdminAction.list")
@ViewDescriptor(path = "admin-action-list-view.xml")
public class AdminActionListView extends StandardListView<AdminAction> {
}