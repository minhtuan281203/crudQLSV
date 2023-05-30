package quanly;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {
    public List<Student> studentList;
    @Override
    public void init() throws ServletException {
        super.init();
        studentList = new ArrayList<>();
        studentList.add(new Student(1, "An", 20, "Ha Noi", "images/anh4.jpg"));
        studentList.add(new Student(2, "Bang", 21, "Ha Noi", "images/anh2.jpg"));
        studentList.add(new Student(3, "Cao", 20, "TP.HCM", "images/anh3.jpg"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "new":
                newStudent(request, response);
                break;
            case "create":
                createStudent(request, response);
                break;
            case "update":
                updateStudent(request, response);
                break;
            case "edit":
                editStudent(request, response);
                break;
            case "delete":
                deleteStudent(request, response);
                break;
            case "search":
                String searchItem = request.getParameter("searchItem");
                List<Student> searchResult = searchStudentByName(searchItem);

                request.setAttribute("studentList", searchResult);
                RequestDispatcher dispatcher = request.getRequestDispatcher("student-view.jsp");
                dispatcher.forward(request, response);
                break;
            default:
                listStudent(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("studentList", studentList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("student-view.jsp");
        dispatcher.forward(request, response);
    }
    private void newStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("student-form.jsp");
        dispatcher.forward(request, response);
    }

    private void createStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ten = request.getParameter("ten");
        int age = Integer.parseInt(request.getParameter("age"));
        int id = studentList.size() + 1;
        String address = request.getParameter("address");

        //handle image upload
        //get the file part from request
        Part filePart = request.getPart("image");
        //get the file name
        String fileName = getFileName(filePart);
        //define the upload directory
        String uploadDirectory = getServletContext().getRealPath("/images");
        //save the file to upload directory
        String filePath = uploadFile(filePart, fileName, uploadDirectory);
        String fileUrl = "images/" + fileName;

        //Create new student
        Student newStudent = new Student(id, ten, age, address, fileUrl);

        //old code:     Student newStudent = new Student(id, ten, age, address);
        studentList.add(newStudent);

        response.sendRedirect("students");
    }
    private void editStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = getStudentById(id);

        request.setAttribute("student", student);
        RequestDispatcher dispatcher = request.getRequestDispatcher("student-form.jsp");
        dispatcher.forward(request, response);
    }
    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String ten = request.getParameter("ten");
        int age = Integer.parseInt(request.getParameter("age"));
        String address = request.getParameter("address");

        Student studentUp = getStudentById(id);
        studentUp.setId(id);
        studentUp.setTen(ten);
        studentUp.setAge(age);
        studentUp.setAddress(address);

        //handle image update
        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            //delete exist image file
            deleteImage(studentUp.getImageUrl(), request);

            //save new image, handle image upload
            //get file part from request
            String fileName = getFileName(filePart);
            String uploadDirectory = getServletContext().getRealPath("/images");
            String filePath = uploadFile(filePart, fileName, uploadDirectory);
            String fileUrl = "images/" + fileName;

            //upload image URL
            studentUp.setImageUrl(fileUrl);
        }

        response.sendRedirect("students");
    }
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = getStudentById(id);

        if (student != null) {
            //delete the associated image file
            deleteImage(student.getImageUrl(), request);

            //delete student from list or DB
            studentList.remove(student);
        }

        response.sendRedirect("students");
    }


    private Student getStudentById(int id) {
        for (Student student : studentList) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    private List<Student> searchStudentByName(String searchItem) {
        List<Student> searchResult = new ArrayList<>();
        for (Student student : studentList) {
            if (student.getTen().toLowerCase().contains(searchItem.toLowerCase())) {
                searchResult.add(student);
            }
        }
        return searchResult;
    }

    private void deleteImage(String imageUrl, HttpServletRequest request) {
        String uploadDirectory = request.getServletContext().getRealPath("") + File.separator + "images";
        String imagePath = uploadDirectory + File.separator + imageUrl;

        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            imageFile.delete();
        }
    }
    private String uploadFile(Part filePart, String fileName, String uploadDirectory) throws IOException {
        String filePath = uploadDirectory + File.separator + fileName;

        try (InputStream inputStream = filePart.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return filePath;
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] elements = contentDisposition.split(";");

        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf("=") + 1).trim().replace("\"","");
            }
        }
        return "";
    }

}

