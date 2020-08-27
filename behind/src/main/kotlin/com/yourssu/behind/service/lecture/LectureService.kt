package com.yourssu.behind.service.lecture

import com.yourssu.behind.model.dto.lecture.LectureCreateDto
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.lecture.LectureSemester
import com.yourssu.behind.model.entity.professor.Professor
import com.yourssu.behind.repository.lecture.LectureRepository
import com.yourssu.behind.repository.professor.ProfessorRepository
import org.apache.poi.hssf.usermodel.*
import org.apache.poi.ss.usermodel.CellType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream


@Service
class LectureService @Autowired constructor(val lectureRepository: LectureRepository,
                                            val professorRepository: ProfessorRepository) {

    fun saveLecture(lectureDto: LectureCreateDto) {
        if (!lectureRepository.existsByLectureCode(lectureDto.lectureCode))
            lectureRepository.save(Lecture(
                    lectureCode = lectureDto.lectureCode,
                    courseName = lectureDto.courseName,
                    major = lectureDto.major,
                    professor = lectureDto.professor,
                    year = lectureDto.year,
                    semester = lectureDto.semester
            ))
    }

    fun readExcel() {
        for (path in getFileList())
            readSheet(path)
    }

    fun readSheet(path: String){
        val filePath = FileInputStream(path)
        val sheetCount = HSSFWorkbook(filePath).numberOfSheets
        for(i in 0 until sheetCount)
            readColumn(path,i)
    }

    fun readColumn(path: String, sheetAt: Int) {
        val filePath = FileInputStream(path)
        var columnIndex = 0
        val sheet = HSSFWorkbook(filePath).getSheetAt(sheetAt)
        val rows = sheet.physicalNumberOfRows
        val row: HSSFRow = sheet.getRow(0)
        var courseNames = arrayListOf<String>()
        var majors = arrayListOf<String>()
        var professors = arrayListOf<String>()
        var lectureCodes = arrayListOf<String>()

        while (true) {
            val cell = row.getCell(columnIndex++)
            if (cell.stringCellValue == "과목번호") lectureCodes = readRow("lectureCodes", path, sheetAt)
            else if (cell.stringCellValue == "과목명") courseNames = readRow("courseNames", path, sheetAt)
            else if (cell.stringCellValue == "교수명") professors = readRow("professors", path, sheetAt)
            else if (cell.stringCellValue == "개설학과") {
                majors = readRow("majors", path, sheetAt)
                break
            }
        }

        for (i in 0 until rows - 1) {
            if (!lectureRepository.existsByLectureCode(lectureCodes[i])) {
                saveLecture(LectureCreateDto(lectureCodes[i], courseNames[i], majors[i],
                        professorRepository.save(Professor(name = professors[i])), year = "2020",
                        semester = LectureSemester.FALL))
            }
        }
    }

    fun readRow(fieldName: String, path: String, sheetAt: Int): ArrayList<String> {
        val result = arrayListOf<String>()
        val filePath = FileInputStream(path)
        val sheet = HSSFWorkbook(filePath).getSheetAt(sheetAt)

        when (fieldName) {
            "lectureCodes" -> {
                for (i in 1 until sheet.physicalNumberOfRows) {
                    when (sheet.getRow(i).getCell(5).cellType) {
                        CellType.STRING -> result.add(sheet.getRow(i).getCell(5).stringCellValue)
                        CellType.NUMERIC -> result.add(sheet.getRow(i).getCell(5).numericCellValue.toLong().toString())
                        else -> result.add("")
                    }
                }
            }
            "courseNames" -> {
                for (i in 1 until sheet.physicalNumberOfRows)
                        result.add(sheet.getRow(i).getCell(6).stringCellValue)
            }
            "professors" -> {
                for (i in 1 until sheet.physicalNumberOfRows)
                    if(sheet.getRow(i).getCell(8).stringCellValue == "")
                        result.add("")
                    else result.add(parseName(sheet.getRow(i).getCell(8).stringCellValue))
            }
            "majors" -> {
                for (i in 1 until sheet.physicalNumberOfRows)
                    result.add(sheet.getRow(i).getCell(9).stringCellValue)
            }
        }
        return result
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun getFileList(): List<String> {
        val path = "behind/Lecture_Excel/"
        val dir = File(path)
        val fileList = dir.listFiles()
        val fileNames = arrayListOf<String>()
        for (file in fileList)
            if (file.isFile)
                fileNames.add(path.plus(file.name))
        return fileNames
    }

    fun parseName(name: String): String {
        val temp = name.split("\n")
        var result = ""
        for (i in temp) {
            if (!result.contains(i)) result = result.plus(i).plus(" ")
        }
        return result
    }

}
