package com.marketplace.admin.view.adminaction;

import com.marketplace.admin.entity.AdminAction;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DefaultMainViewParent;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "admin-actions/:id", layout = DefaultMainViewParent.class)
@ViewController(id = "admin_AdminAction.detail")
@ViewDescriptor(path = "admin-action-detail-view.xml")
public class AdminActionDetailView extends StandardDetailView<AdminAction> {
}