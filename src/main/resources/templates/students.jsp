
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student List and Registration</title>
    <link th:href="@{/style.css}" rel="stylesheet" />
</head>
<body>
    <div id="registrationForm">
        <h2>Student Registration Form</h2>
        <form  id="registrationForm1" action="#" th:action="@{/students}" th:object="${student}" method="post">
            <div>
                <div id="label">
                <label for="name">Name:</label>
                </div>
                <div id="input">
                <input type="text" id="name" name="name" required>
                 </div>
            </div>
            <div>
            <div id="label">
                <label for="age">Age:</label>
                </div>
                                <div id="input">
                <input type="number" id="age" name="age" required>
                 </div>
            </div>
            <div>
            <div id="label">
                <label for="course">Course:</label>
                </div>
                                <div id="input">
                <input type="text" id="course" name="course" required>
                 </div>
            </div>
            <div>
            <div id="label">
                <label for="gender">Gender:</label>
                </div>
                <div id="input">
                <input class="radio" type="radio" id="male" name="gender" value="Male">
                <label for="male">Male</label>
                <input  class="radio"  type="radio" id="female" name="gender" value="Female" >
                <label for="female">Female</label>
                 </div>
            </div>
            <div>
            <div id="label">
                <label for="dateOfBirth">Date of Birth:</label>
                </div>
                                <div id="input">
                <input type="date" id="dateOfBirth" name="dateOfBirth" required>
                 </div>
            </div>
            <div>
            <div id="label">
                <label for="address">Address:</label>
                </div>
                                <div id="input">
                <input type="text" id="address" name="address" required>
                 </div>
            </div>
            <div>
            <div id="label"></div>
            <div id="button">
            <button type="submit">Submit</button>
            </div>
            </div>
        </form>
    </div>
    <div id="editForm" style="display: none;">
    <h2>Student Registration Form</h2>
            <form id="editForm1" >
                <div>
                <div id="label">
                      <label for="id">ID:</label>
                      </div>
                                      <div id="input">
                      <input type="text" id="id" name="id" disabled>
                       </div>
                </div>
                <div>
                <div id="label">
                    <label for="name">Name:</label>
                    </div>
                                    <div id="input">
                    <input type="text" id="editName" name="name" required>
                     </div>
                </div>
                <div>
                <div id="label">
                    <label for="age">Age:</label>
                    </div>
                                    <div id="input">
                    <input type="number" id="editAge" name="age" required>
                     </div>
                </div>
                <div>
                <div id="label">
                    <label for="course">Course:</label>
                    </div>
                                    <div id="input">
                    <input type="text" id="editCourse" name="course" required>
                     </div>
                </div>
                <div>
                <div id="label">
                    <label for="gender">Gender:</label>
                    </div>
                    <div id="input">
                                          <input class="radio" type="radio" id="editMale" name="gender" value="Male">
                                          <label for="editMale">Male</label>
                                          <input  class="radio"  type="radio" id="editFemale" name="gender" value="Female" >
                                          <label for="editFemale">Female</label>
                    </div>
                </div>
                <div>
                <div id="label">
                    <label for="dateOfBirth">Date of Birth:</label>
                    </div>
                                    <div id="input">
                    <input type="date" id="editDateOfBirth" name="dateOfBirth" required>
                     </div>
                </div>
                <div>
                <div id="label">
                    <label for="address">Address:</label>
                    </div>
                                    <div id="input">
                    <input type="text" id="editAddress" name="address" required>
                     </div>
                </div>
                <div>
                <div id="label"></div>
                <div id="button">
                                    <button th:onclick="goEdit()">Submit</button>
                                </div>
                </div>



            </form>
        </div>
    <div id="list">
    <th:block th:if="${students != null and not #lists.isEmpty(students)}">
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Age</th>
                    <th>Course</th>
                    <th>Gender</th>
                    <th>Date of Birth</th>
                    <th>Address</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                    <tr th:each="student: ${students}">
                        <td><span th:text="${student.id}" /></td>
                        <td><span th:text="${student.name}" /></td>
                        <td><span th:text="${student.age}" /></td>
                        <td><span th:text="${student.course}" /></td>
                        <td><span th:text="${student.gender}" /></td>
                        <td><span th:text="${student.dateOfBirth}" /></td>
                        <td><span th:text="${student.address}" /></td>
                        <td>
                            <button th:onclick="editStudent([[${student.id}]])">Edit</button>
                            <button th:onclick="deleteStudent([[${student.id}]])">Delete</button>
                            <button th:onclick="print([[${student.id}]])">Print</button>
                        </td>
                    </tr>
            </tbody>
        </table>
        </th:block>
        </div>
        <script  th:inline="javascript">
            function editStudent(id) {
                    fetch('/students/' + id, {method: 'GET'})
                        .then(response => response.json())
                        .then(data => {
                            document.getElementById('editForm').style.display = 'block';
                            document.getElementById('registrationForm').style.display = 'none';
                            document.getElementById('id').value = data.id;
                           document.getElementById('editName').value = data.name;
                           document.getElementById('editAge').value = data.age;
                           document.getElementById('editCourse').value = data.course;
                           document.getElementById('editDateOfBirth').value = data.dateOfBirth;
                           document.getElementById('editAddress').value = data.address;
                           const genderValue = data.gender;
                           if (genderValue == 'Male') {
                               document.getElementById('editMale').checked = true;
                           } else if (genderValue == 'Female') {
                               document.getElementById('editFemale').checked = true;
                           }
                        })
                        .catch(error => console.error('Error:', error));
            }
            function goEdit() {
                const genderInputs = document.getElementsByName('gender');
                let selectedGender = '';
                genderInputs.forEach(input => {
                    if (input.checked) {
                        selectedGender = input.value;
                    }
                });
                const student = {
                    id: document.getElementById('id').value,
                    name: document.getElementById('editName').value,
                    age: document.getElementById('editAge').value,
                    course: document.getElementById('editCourse').value,
                    gender: selectedGender,
                    dateOfBirth: document.getElementById('editDateOfBirth').value,
                    address: document.getElementById('editAddress').value
                };

                const studentId = student.id;

                fetch('/students/' + studentId, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(student)
                })
                .then(response => {
                    if (response.ok) {
                        window.location.href = '/';
                        console.log("Update success!")
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
            }
            function deleteStudent(id) {
                fetch('/students/' + id, {
                            method: 'DELETE'
                        }).then(response => {
                            if (response.status === 204) {
                                window.location.href = '/';
                                console.log("Delete success!")
                            }
                        }).catch(error => {
                            console.error('Error:', error);
                        });

            }
            function print(id) {
                            fetch('/student-report/pdf' + id, {
                                        method: 'GET'
                                    }).then(response => {
                                       if (response.ok) {

                                                               console.log("Print Success!")
                                                           }
                                    }).catch(error => {
                                        console.error('Error:', error);
                                    });

                        }

        </script>
</body>

</html>
