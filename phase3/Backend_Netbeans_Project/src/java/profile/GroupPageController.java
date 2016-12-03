package profile;

import profile.util.JsfUtil;
import profile.util.PaginationHelper;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import profile.util.DataConnect;
import profile.util.SessionUtils;

@Named("groupPageController")
@SessionScoped
public class GroupPageController implements Serializable {

    private GroupPage current;
    private DataModel items = null;
    @EJB
    private profile.GroupPageFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private static String groupInputContent = "";

    public GroupPageController() {
    }

    public GroupPage getSelected() {
        if (current == null) {
            current = new GroupPage();
            selectedItemIndex = -1;
        }
        return current;
    }

    private GroupPageFacade getFacade() {
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
        current = (GroupPage) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new GroupPage();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GroupPageCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (GroupPage) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GroupPageUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (GroupPage) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GroupPageDeleted"));
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

    public GroupPage getGroupPage(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     * @return the inputContent
     */
    public String getGroupInputContent() {
        return groupInputContent;
    }

    /**
     * @param inputContent the inputContent to set
     */
    public void setGroupInputContent(String groupInputContent) {
        this.groupInputContent = groupInputContent;
    }

    @FacesConverter(forClass = GroupPage.class)
    public static class GroupPageControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GroupPageController controller = (GroupPageController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "groupPageController");
            return controller.getGroupPage(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof GroupPage) {
                GroupPage o = (GroupPage) object;
                return getStringKey(o.getPageId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + GroupPage.class.getName());
            }
        }

        public String createGroup() {
            if (!groupInputContent.isEmpty()) {
                Connection con = null;
                // insert post
                PreparedStatement ps = null;
                //find page id where it is posted
                PreparedStatement ps2 = null;
                // insert to posted To
                PreparedStatement ps3 = null;

                try {
                    con = DataConnect.getConnection();
                    // give proper variables and propert format
                    ps = con.prepareStatement("INSERT INTO Comment(CommentID, DateCreated, Content, AuthorID) VALUES (?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
                    ps.setNull(1, java.sql.Types.INTEGER);
                    ps.setTimestamp(2, java.sql.Timestamp.from(java.time.Instant.now()));
                    ps.setInt(4, SessionUtils.getUserId());

                    // print out the query statement
                    JsfUtil.addErrorMessage(ps.toString());
                    // Execute the Insert Query
                    ps.executeUpdate();
                    // Find the most recent postID due to autoincrement
                    ResultSet keyResultSet = ps.getGeneratedKeys();
                    // initalized newestpost 
                    int newestpostID = -1;
                    while (keyResultSet.next()) {
                        // get newest post
                        newestpostID = keyResultSet.getInt(1);
                    }
                    //close connection to save on resources
                    ps.close();
                    // prepare for getting page id 

                    ps2 = con.prepareStatement("INSERT INTO CommentOn(CommentID, PostID) VALUES (?, ?);");
                    ps2.setInt(1, newestpostID);
                    ps2.setInt(2, SessionUtils.getPost().getPostId());

                    ps2.executeUpdate();

                    ps2.close();


                } catch (SQLException ex) {

                    // print out error message
                    JsfUtil.addErrorMessage("Error occured while commenting" + ex.getMessage());

                } finally {
                    DataConnect.close(con);
                }

            }
            return "/groupPage/CreateAGroup";

        }
    }
    

}
