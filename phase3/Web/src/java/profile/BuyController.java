package profile;


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
import tmp.util.JsfUtil;
import tmp.util.PaginationHelper;

@Named("buyController")
@SessionScoped
public class BuyController implements Serializable {

    private Buy current;
    private DataModel items = null;
    @EJB
    private profile.BuyFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public BuyController() {
    }

    public Buy getSelected() {
        if (current == null) {
            current = new Buy();
            current.setBuyPK(new profile.BuyPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private BuyFacade getFacade() {
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
        current = (Buy) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Buy();
        current.setBuyPK(new profile.BuyPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getBuyPK().setUserId(current.getUserPlus().getUserId());
            current.getBuyPK().setEmpId(current.getEmployee().getSsn());
            current.getBuyPK().setTransId(current.getSales().getTransId());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BuyCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Buy) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getBuyPK().setUserId(current.getUserPlus().getUserId());
            current.getBuyPK().setEmpId(current.getEmployee().getSsn());
            current.getBuyPK().setTransId(current.getSales().getTransId());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BuyUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Buy) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BuyDeleted"));
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

    public Buy getBuy(profile.BuyPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Buy.class)
    public static class BuyControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BuyController controller = (BuyController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "buyController");
            return controller.getBuy(getKey(value));
        }

        profile.BuyPK getKey(String value) {
            profile.BuyPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new profile.BuyPK();
            key.setTransId(Integer.parseInt(values[0]));
            key.setEmpId(values[1]);
            key.setUserId(Integer.parseInt(values[2]));
            return key;
        }

        String getStringKey(profile.BuyPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getTransId());
            sb.append(SEPARATOR);
            sb.append(value.getEmpId());
            sb.append(SEPARATOR);
            sb.append(value.getUserId());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Buy) {
                Buy o = (Buy) object;
                return getStringKey(o.getBuyPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Buy.class.getName());
            }
        }

    }

}
