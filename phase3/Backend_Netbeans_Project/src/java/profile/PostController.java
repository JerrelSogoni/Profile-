package profile;

import profile.util.JsfUtil;
import profile.util.PaginationHelper;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javax.servlet.http.HttpSession;
import profile.util.DataConnect;
import profile.util.SessionUtils;

@Named("postController")
@SessionScoped
public class PostController implements Serializable {

    private Post current;
    private DataModel items = null;
    @EJB
    private profile.PostFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public PostController() {
    }

    public Post getSelected() {
        if (current == null) {
            current = new Post();
            selectedItemIndex = -1;
        }
        return current;
    }

    private PostFacade getFacade() {
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
        current = (Post) getItems().getRowData();
        PaginationHelper ph = getPagination();
        selectedItemIndex = ph.getPageFirstItem() + getItems().getRowIndex();
        return "/post/PostOwner";
    }
    
    public String prepareViewFromNewfeed() {
        current = (Post) getItems().getRowData();
        PaginationHelper ph = getPagination();
        selectedItemIndex = ph.getPageFirstItem() + getItems().getRowIndex();
        return "/post/PostOwner";
    }

    public String prepareCreate() {
        current = new Post();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PostCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Post) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PostUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Post) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PostDeleted"));
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

//    public DataModel getItems() {
//        if (items == null) {
//            items = getPagination().createPageDataModel();
//        }
//        return items;
//    }
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

    public Post getPost(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Post.class)
    public static class PostControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PostController controller = (PostController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "postController");
            return controller.getPost(getKey(value));
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
            if (object instanceof Post) {
                Post o = (Post) object;
                return getStringKey(o.getPostId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Post.class.getName());
            }
        }

    }

    /*Daniel Added*/
    private DataModel myItems;

    public void setMyItems(DataModel myItems) {
        this.myItems = myItems;
    }

    public DataModel getItems() {
        if (myItems == null) {
            HttpSession session = SessionUtils.getSession();
            String username = (String) session.getAttribute("username");
            myItems = new ListDataModel(getPostsBy(username));
            JsfUtil.addErrorMessage("Logged in as " + username);
        }
        return myItems;
    }

    public List<Post> getPostsBy(String user) {
        ArrayList<Post> toRet = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    Post\n"
                    + "WHERE\n"
                    + "    Post.PostId IN (SELECT \n"
                    + "            PostId\n"
                    + "        FROM\n"
                    + "            PostedTo\n"
                    + "        WHERE\n"
                    + "            PostedTo.PageId = (SELECT \n"
                    + "                    PersonalPage.PageId\n"
                    + "                FROM\n"
                    + "                    PersonalPage\n"
                    + "                WHERE\n"
                    + "                    PersonalPage.ownerid = (SELECT \n"
                    + "                            UserId\n"
                    + "                        FROM\n"
                    + "                            UserPlus\n"
                    + "                        WHERE\n"
                    + "                            UserId = ?)));");
            ps.setString(1, user);

            // print out the query statement
            JsfUtil.addErrorMessage(ps.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //result found, means valid inputs
                Post added = new Post();
                added.setContent(rs.getString("Content"));
                toRet.add(added);
                JsfUtil.addErrorMessage("Added to return list " + rs.getString("Content"));
            }
        } catch (SQLException ex) {

            // print out error message
            JsfUtil.addErrorMessage("Connection to database failed:" + ex.getMessage());
            System.out.println("Login error -->" + ex.getMessage());
            return null;

        } finally {
            DataConnect.close(con);
        }
        return toRet;
    }
}
