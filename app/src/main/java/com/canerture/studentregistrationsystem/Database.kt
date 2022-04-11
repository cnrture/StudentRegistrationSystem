package com.canerture.studentregistrationsystem

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(context, databaseName, null, databaseVersion) {

    companion object {
        private const val databaseName = "AllDatas"
        private const val databaseVersion = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE students (id INTEGER PRIMARY KEY AUTOINCREMENT, studentid TEXT, gender TEXT, name TEXT, surname TEXT, faculty TEXT, department TEXT, lecturer TEXT) ")
        db?.execSQL("CREATE TABLE faculties (id INTEGER PRIMARY KEY AUTOINCREMENT, faculty TEXT) ")
        db?.execSQL("CREATE TABLE departments (id INTEGER PRIMARY KEY AUTOINCREMENT, department TEXT) ")
        db?.execSQL("CREATE TABLE lecturers (id INTEGER PRIMARY KEY AUTOINCREMENT, lecturer TEXT) ")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun addStudent(student: StudentModel) {
        val db: SQLiteDatabase = writableDatabase
        val values = ContentValues()
        values.put("studentid", student.studentId)
        values.put("gender", student.gender)
        values.put("name", student.name)
        values.put("surname", student.surname)
        values.put("faculty", student.faculty)
        values.put("department", student.department)
        values.put("lecturer", student.lecturer)
        db.insert("students", null, values)
        db.close()
    }

    fun addFaculty(faculty: String) {
        val db: SQLiteDatabase = writableDatabase
        val values = ContentValues()
        values.put("faculty", faculty)
        db.insert("faculties", null, values)
        db.close()
    }

    fun addDepartment(department: String) {
        val db: SQLiteDatabase = writableDatabase
        val values = ContentValues()
        values.put("department", department)
        db.insert("departments", null, values)
        db.close()
    }

    fun addLecturer(lecturer: String) {
        val db: SQLiteDatabase = writableDatabase
        val values = ContentValues()
        values.put("lecturer", lecturer)
        db.insert("lecturers", null, values)
        db.close()
    }

    fun updateStudent(updatedStudent: StudentModel) {
        val db: SQLiteDatabase = writableDatabase
        val values = ContentValues()
        values.put("studentid", updatedStudent.studentId)
        values.put("gender", updatedStudent.gender)
        values.put("name", updatedStudent.name)
        values.put("surname", updatedStudent.surname)
        values.put("faculty", updatedStudent.faculty)
        values.put("department", updatedStudent.department)
        values.put("lecturer", updatedStudent.lecturer)
        db.update("students", values, "id=${updatedStudent.id}", null)
        db.close()
    }

    fun getStudents(): ArrayList<StudentModel> {
        val db: SQLiteDatabase = readableDatabase
        val columns = arrayOf(
            "id",
            "studentid",
            "gender",
            "name",
            "surname",
            "faculty",
            "department",
            "lecturer"
        )
        val cursor: Cursor = db.query("students", columns, null, null, null, null, null)

        val registeredStudents = arrayListOf<StudentModel>()

        val idIx = cursor.getColumnIndex("id")
        val studentIdIx = cursor.getColumnIndex("studentid")
        val genderIx = cursor.getColumnIndex("gender")
        val nameIx = cursor.getColumnIndex("name")
        val surnameIx = cursor.getColumnIndex("surname")
        val facultyIx = cursor.getColumnIndex("faculty")
        val departmentIx = cursor.getColumnIndex("department")
        val lecturerIx = cursor.getColumnIndex("lecturer")

        while (cursor.moveToNext()) {
            registeredStudents.add(
                StudentModel(
                    cursor.getInt(idIx),
                    cursor.getString(studentIdIx),
                    cursor.getString(genderIx),
                    cursor.getString(nameIx),
                    cursor.getString(surnameIx),
                    cursor.getString(facultyIx),
                    cursor.getString(departmentIx),
                    cursor.getString(lecturerIx),
                )
            )
        }

        cursor.close()
        db.close()

        return registeredStudents
    }

    fun getFaculties(): ArrayList<String> {
        val db: SQLiteDatabase = readableDatabase
        val columns = arrayOf("id", "faculty")
        val cursor: Cursor = db.query("faculties", columns, null, null, null, null, null)

        val faculties = arrayListOf<String>()

        val facultyIx = cursor.getColumnIndex("faculty")

        while (cursor.moveToNext()) {
            faculties.add(cursor.getString(facultyIx))
        }

        cursor.close()
        db.close()

        return faculties
    }

    fun getDepartments(): ArrayList<String> {
        val db: SQLiteDatabase = readableDatabase
        val columns = arrayOf("id", "department")
        val cursor: Cursor = db.query("departments", columns, null, null, null, null, null)

        val departments = arrayListOf<String>()

        val departmentIx = cursor.getColumnIndex("department")

        while (cursor.moveToNext()) {
            departments.add(cursor.getString(departmentIx))
        }

        cursor.close()
        db.close()

        return departments
    }

    fun getLecturers(): ArrayList<String> {
        val db: SQLiteDatabase = readableDatabase
        val columns = arrayOf("id", "lecturer")
        val cursor: Cursor = db.query("lecturers", columns, null, null, null, null, null)

        val lecturers = arrayListOf<String>()

        val lecturerIx = cursor.getColumnIndex("lecturer")

        while (cursor.moveToNext()) {
            lecturers.add(cursor.getString(lecturerIx))
        }

        cursor.close()
        db.close()

        return lecturers
    }
}