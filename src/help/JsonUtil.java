/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package help;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonReader;
import model.*;

public class JsonUtil {

    public static String convertFromJsonEmail(JsonObject obj) {
        return obj.getString(JsonConst.EMAIL);
    }

    public static JsonObject convertToJsonEmailResponse(int id) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, JsonConst.TYPE_EMAIL_SIGNIN_RESPONSE)
                .add("id", id)
                .build();
        return obj;
    }

    public static String convertFromJsonPassword(JsonObject obj) {
        return obj.getString(JsonConst.PASSWORD);
    }

    public static JsonObject convertToJsonPasswordResponse(boolean b) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, JsonConst.TYPE_EMAIL_SIGNIN_RESPONSE) //NotUse
                .add(JsonConst.TYPE_PASSWORD_SIGNIN_RESPONSE, b)
                .build();
        return obj;
    }

    public static JsonObject convertFromString(String string) {
        JsonReader jsonReader = Json.createReader(new StringReader(string));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        return jsonObject;
    }

    public static int convertFromJsonId(JsonObject obj) {
        return obj.getInt(JsonConst.ID);
    }

    //Don't Have Online_Status
    public static UserModel converetFromJsonUserModel(JsonObject obj) {
        UserModel user = new UserModel();
        user.setId(obj.getInt("id"));
        user.setName(obj.getString("name"));
        user.setEmail(obj.getString("email"));
        user.setPassword(obj.getString("password"));
        return user;
    }

    public static TaskModel toTaskModel(JsonObject obj) {
        TaskModel task = new TaskModel();
        task.setTitle(obj.getString("title"));
        task.setDescription(obj.getString("description"));
        task.setTask_status(obj.getString("task_status"));
        task.setAssign_date(Timestamp.valueOf(obj.getString("assign_date")));
        task.setDeadline(Timestamp.valueOf(obj.getString("deadline")));
        task.setList_id(obj.getInt("list_id"));
        task.setUser_id(obj.getInt("user_id"));
        task.setAssign_status(obj.getString("assign_status"));
        return task;
    }

    public static TaskModel toUpdateTaskModel(JsonObject obj) {
        TaskModel task = new TaskModel();
        task.setTask_id(obj.getInt("task_id"));
        task.setTitle(obj.getString("title"));
        task.setDescription(obj.getString("description"));
        task.setTask_status(obj.getString("task_status"));
        task.setAssign_date(Timestamp.valueOf(obj.getString("assign_date")));
        task.setDeadline(Timestamp.valueOf(obj.getString("deadline")));
        task.setList_id(obj.getInt("list_id"));
        task.setUser_id(obj.getInt("user_id"));
        task.setAssign_status(obj.getString("assign_status"));
        return task;
    }

    public JsonArrayBuilder createJsonArrayFromList(List<UserModel> users) {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        for (UserModel user : users) {
            jsonArray.add(Json.createObjectBuilder()
                    .add("id", user.getId())
                    .add("name", user.getName())
                    .add("email", user.getEmail())
                    .add("online_status", user.getOnline_status()));
        }
        jsonArray.build();

        return jsonArray;
    }

    public static JsonObject fromListOfUsers(List<UserModel> users) {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        for (UserModel user : users) {
            jsonArray.add(Json.createObjectBuilder()
                    .add("id", user.getId())
                    .add("name", user.getName())
                    .add("email", user.getEmail())
                    .add("online_status", user.getOnline_status())
                    .build());
        }
        JsonArray jArr = jsonArray.build();

        JsonObject obj = Json.createObjectBuilder()
                .add("type", JsonConst.TYPE_FRIENDS_LIST)
                .add("array", jArr)
                .build();
        return obj;
    }

    public static CollaboratorModel toCollaborator(JsonObject obj) {
        CollaboratorModel collaboratorModel = new CollaboratorModel(obj.getInt(JsonConst.LIST_ID),
                obj.getInt(JsonConst.USER_ID));
        return collaboratorModel;
    }

    public static JsonObject fromId(int id) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.ID, id)
                .build();
        return obj;
    }

    public static CommentModel toCommentModel(JsonObject obj) {
        CommentModel comment = new CommentModel();
        comment.setTask_id(obj.getInt("task_id"));
        comment.setUser_id(obj.getInt("user_id"));
        comment.setComment_text(obj.getString("comment_text"));
        comment.setComment_date(Timestamp.valueOf(obj.getString("comment_date")));
        return comment;
    }

    public static JsonObject fromListOfComments(List<CommentModel> comments) {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        for (CommentModel comment : comments) {
            jsonArray.add(Json.createObjectBuilder()
                    .add("name", comment.getUserName())
                    .add("comment_date", comment.getComment_date().toString())
                    .add("comment_text", comment.getComment_text())
                    .build());
        }
        JsonArray jArr = jsonArray.build();

        JsonObject obj = Json.createObjectBuilder()
                .add("array", jArr)
                .build();
        return obj;
    }

    public static ListModel toListModel(JsonObject obj) {
        ListModel listModel = new ListModel();
        listModel.setList_id(obj.getInt("list_id"));
        listModel.setTitle(obj.getString("title"));
        listModel.setColor(obj.getString("color"));
        listModel.setCreate_date(Timestamp.valueOf(obj.getString("create_date")));
        listModel.getUser().setId(obj.getInt("user_id"));
        return listModel;
    }

    public static JsonObject fromBoolean(boolean b) {
        JsonObject obj = Json.createObjectBuilder()
                .add("status", b)
                .build();
        return obj;
    }

    public static boolean toBoolean(JsonObject obj) {
        return obj.getBoolean("status");
    }

    public static int getID(JsonObject obj) {
        return obj.getInt(JsonConst.ID);
    }

    public static JsonObject fromListOfListModels(List<ListModel> lists) {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        for (ListModel list : lists) {
            jsonArray.add(Json.createObjectBuilder()
                    .add("id", list.getList_id())
                    .add("title", list.getTitle())
                    .add("color", list.getColor())
                    .add("createDate", list.getCreate_date().toString())
                    .add("userid", list.getUser().getId())
                    .add("username", list.getUser().getName())
                    .add("useremail", list.getUser().getEmail())
                    .add("userstates", list.getUser().getOnline_status())
                    .build());
        }
        JsonArray jArr = jsonArray.build();

        JsonObject obj = Json.createObjectBuilder()
                .add("type", JsonConst.TYPE_SELECT_ALL_LIST)
                .add("array", jArr)
                .build();
        return obj;
    }

    public static JsonObject fromListOfTasks(List<TaskModel> tasks) {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        for (TaskModel task : tasks) {
            jsonArray.add(Json.createObjectBuilder()
                    .add(JsonConst.TYPE, JsonConst.TYPE_GET_ALL_TASKS)
                    .add("task_id", task.getTask_id())
                    .add("title", task.getTitle())
                    .add("description", task.getDescription())
                    .add("task_status", task.getTask_status())
                    .add("deadline", task.getDeadline().toString())
                    .add("list_id", task.getList_id())
                    .add("user_id", task.getUser_id())
                    .add("assign_date", task.getAssign_date().toString())
                    .add("assign_status", task.getAssign_status())
                    .add("user_name", task.getUser_name())
                    .build());
        }
        JsonArray jArr = jsonArray.build();

        JsonObject obj = Json.createObjectBuilder()
                .add("type", JsonConst.TYPE_GET_ALL_TASKS)
                .add("array", jArr)
                .build();
        return obj;
    }

    public static JsonObject convertToJsonUser(String type, UserModel user) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, type)
                .add("id", user.getId())
                .add("name", user.getName())
                .add("email", user.getEmail())
                .add("online_status", user.getOnline_status())
                .build();
        return obj;
    }

    public static TeammateModel toTeammateModel(JsonObject obj) {
        TeammateModel teammateModel = new TeammateModel();
        teammateModel.setUser_id_1(obj.getInt("user_id_1"));
        teammateModel.setUser_id_2(obj.getInt("user_id_2"));
        teammateModel.setTeammate_status(obj.getString("teammate_status"));
        return teammateModel;
    }

    public static JsonObject fromListOfTaskRequests(List<TaskModel> tasks) {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        for (TaskModel task : tasks) {
            jsonArray.add(Json.createObjectBuilder()
                    .add("title", task.getTitle())
                    .add("deadline", task.getDeadline().toString())
                    .add("user_id", task.getUser_id())
                    .add("task_id", task.getTask_id())
                    .add("assign_date", task.getAssign_date().toString())
                    .build());
        }
        JsonArray jArr = jsonArray.build();

        JsonObject obj = Json.createObjectBuilder()
                .add("type", JsonConst.TYPE_GET_ALL_TASKS)
                .add("array", jArr)
                .build();
        return obj;
    }

    public static JsonObject fromListOfNotifications(List<NotificationModel> notifications) {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        for (NotificationModel notification : notifications) {
            jsonArray.add(Json.createObjectBuilder()
                    .add("getNotification", notification.getNotification())
                    .build());
        }
        JsonArray jArr = jsonArray.build();

        JsonObject obj = Json.createObjectBuilder()
                .add("type", JsonConst.TYPE_GET_NOTIFICATION)
                .add("array", jArr)
                .build();
        return obj;
    }

    public static JsonObject fromStatisticArray(int[] arrayOfActivity) {
        JsonObject obj = Json.createObjectBuilder()
                .add("all_lists", arrayOfActivity[0])
                .add("all_tasks", arrayOfActivity[1])
                .add("todo_tasks", arrayOfActivity[2])
                .add("in_progress_tasks", arrayOfActivity[3])
                .add("done_tasks", arrayOfActivity[4])
                .build();
        return obj;
    }
}
