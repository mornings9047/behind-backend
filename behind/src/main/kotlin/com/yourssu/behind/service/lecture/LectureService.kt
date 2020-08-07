package com.yourssu.behind.service.lecture

import com.yourssu.behind.model.dto.lecture.LectureDto
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.lecture.LectureSemester
import com.yourssu.behind.model.entity.professor.Professor
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.repository.professor.ProfessorRepository
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.File
import java.io.FileInputStream

@Service
class LectureService @Autowired constructor(val lectureRepository : LectureRepository, val professorRepository : ProfessorRepository) {

    @Transactional
    fun saveLecture(lectureDto: LectureDto){
        lectureRepository.save(Lecture(
                courseName = lectureDto.courseName,
                major = lectureDto.major,
                professor = lectureDto.professor,
                year = lectureDto.year,
                semester = lectureDto.semester
        ))
    }

    @Transactional
    fun readExcel() {
        var fileList: ArrayList<String> = getFileList()
        for (path in fileList)
            readColumn(path)
    }

    @Transactional
    fun readColumn(path:String){
        val filePath = FileInputStream(path)
        val wb = HSSFWorkbook(filePath)
        var rowIndex: Int = 0
        var columnIndex: Int = 0
        val sheet = wb.getSheetAt(0)
        val rows = sheet.physicalNumberOfRows
        var row: HSSFRow = sheet.getRow(rowIndex)
        var cell: HSSFCell = row.getCell(columnIndex)
        var courseNames = arrayListOf<String>()
        var majors = arrayListOf<String>()
        var professors:ArrayList<String> = arrayListOf<String>()
        var k = 0

        while (true) {
            cell = row.getCell(columnIndex)
            if (cell.stringCellValue.equals("과목명"))
                courseNames = readRow("courseNames",path)

            else if (cell.stringCellValue.equals("교수명"))
                professors = readRow("professors",path)

            else if (cell.stringCellValue.equals("개설학과")) {
                majors = readRow("majors",path)
                break
            }
            columnIndex++
        }

        while(k < rows-1) {
            var professor = professorRepository.save(Professor(name = professors.get(k)))
            var lectureDto: LectureDto = LectureDto(
                    courseNames.get(k),
                    majors.get(k),
                    professor,
                    year = "2020",
                    semester = LectureSemester.FALL
            )
            saveLecture(lectureDto)
            k++
        }
    }

    @Transactional
    fun readRow(fieldName : String, path:String) : ArrayList<String> {
        var result = arrayListOf<String>()
        val filePath = FileInputStream(path)
        val wb = HSSFWorkbook(filePath)
        var rowIndex: Int = 1
        var columnIndex: Int = 1
        val sheet = wb.getSheetAt(0)
        val rows = sheet.physicalNumberOfRows
        var row: HSSFRow
        var cell: HSSFCell

        var courseNames = arrayListOf<String>()
        var majors = arrayListOf<String>()
        var professors:ArrayList<String> = arrayListOf<String>()

        if (fieldName.equals("courseNames")) {
            rowIndex = 1
            columnIndex = 6
            while (rowIndex < rows) {
                row = sheet.getRow(rowIndex)
                cell = row.getCell(columnIndex)
                courseNames.add(cell.stringCellValue + "")
                rowIndex++
            }
            result = courseNames
            return result
        }

        else if (fieldName.equals("professors")) {
            rowIndex = 1
            columnIndex = 8
            while (rowIndex < rows) {
                row = sheet.getRow(rowIndex)
                cell = row.getCell(columnIndex)
                var value : String = cell.stringCellValue + ""
                value = value.replace("\n",",")
                professors.add(value)
                rowIndex++
            }
            result = professors
            return result
        }

        else if (fieldName.equals("majors")) {
            rowIndex = 1
            columnIndex = 9
            while (rowIndex < rows) {
                row = sheet.getRow(rowIndex)
                cell = row.getCell(columnIndex)
                majors.add(cell.stringCellValue + "")
                rowIndex++
            }
            result = majors
            return result
        }
        return result
    }

    @Transactional
    fun getFileList() : ArrayList<String>{
        val path:String = "./behind/Lecture_Excel"
        val dir: File = File(path)
        var fileList = dir.listFiles()
        var fileNames = arrayListOf<String>()
        for(file in fileList){
            if(file.isFile())
                fileNames.add(path.plus(file.getName()))
        }
        return fileNames
    }
}
