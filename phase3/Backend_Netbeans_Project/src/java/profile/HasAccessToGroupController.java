package profile;

import profile.util.JsfUtil;
import profile.util.PaginationHelper;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("hasAccessToGroupController")
@SessionScoped
public class HasAccessToGroupController implements Serializable {

    private HasAccessToGroup current;
    private DataModel items = null;
    @EJB
    private profile.HasAccessToGroupFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public HasAccessToGroupController() {
    }

    public HasAccessToGroup getSelected() {
        if (current == null) {
            current = new HasAccessToGroup();
            current.setHasAccessToGroupPK(new profile.HasAccessToGroupPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private HasAccessToGroupFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (HasAccessToGroup) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new HasAccessToGroup();
        current.setHasAccessToGroupPK(new profile.HasAccessToGroupPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getHasAccessToGroupPK().setPageId(current.getPagePlus().getPageId());
            current.getHasAccessToGroupPK().setUserId(current.getUserPlus().getUserId());
            current.getHasAccessToGroupPK().setGroupId(current.getGroupPlus().getGroupId());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("HasAccessToGroupCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (HasAccessToGroup) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getHasAccessToGroupPK().setPageId(current.getPagePlus().getPageId());
            current.getHasAccessToGroupPK().setUserId(current.getUserPlus().getUserId());
            current.getHasAccessToGroupPK().setGroupId(current.getGroupPlus().getGroupId());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("HasAccessToGroupUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (HasAccessToGroup) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("HasAccessToGroupDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public HasAccessToGroup getHasAccessToGroup(profile.HasAccessToGroupPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = HasAccessToGroup.class)
    public static class HasAccessToGroupControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HasAccessToGroupController controller = (HasAccessToGroupController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "hasAccessToGroupController");
            return controller.getHasAccessToGroup(getKey(value));
        }

        profile.HasAccessToGroupPK getKey(String value) {
            profile.HasAccessToGroupPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new profile.HasAccessToGroupPK();
            key.setUserId(Integer.parseInt(values[0]));
            key.setPageId(Integer.parseInt(values[1]));
            key.setGroupId(Integer.parseInt(values[2]));
            return key;
        }

        String getStringKey(profile.HasAccessToGroupPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getUserId());
            sb.append(SEPARATOR);
            sb.append(value.getPageId());
            sb.append(SEPARATOR);
            sb.append(value.getGroupId());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof HasAccessToGroup) {
                HasAccessToGroup o = (HasAccessToGroup) object;
                return getStringKey(o.getHasAccessToGroupPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + HasAccessToGroup.class.getName());
            }
        }

    }

}
