package com.example.karan.data.repository

import com.example.karan.data.local.dao.ErreurQuestionDao
import com.example.karan.data.local.dao.FicheDao
import com.example.karan.data.local.dao.MatiereDao
import com.example.karan.data.local.dao.QuestionDao
import com.example.karan.data.local.dao.SessionQuizDao
import com.example.karan.models.ErreurQuestion
import com.example.karan.models.Fiche
import com.example.karan.models.Matiere
import com.example.karan.models.Question
import com.example.karan.models.SessionQuiz

class RevisionRepositoryImpl(
    private val matiereDao: MatiereDao,
    private val questionDao: QuestionDao,
    private val sessionQuizDao: SessionQuizDao,
    private val ficheDao: FicheDao,
    private val erreurQuestionDao: ErreurQuestionDao
) : RevisionRepository {

    override suspend fun getMatieres(
        examen: String
    ): List<Matiere> {
        return matiereDao.getMatieres(examen)
    }

    override suspend fun getMatiere(
        matiereId: Long
    ): Matiere? {
        return matiereDao.getMatiere(matiereId)
    }

    override suspend fun getQuestions(
        examen: String,
        matiereId: Long,
        nombreQuestions: Int
    ): List<Question> {
        return questionDao.getQuestions(
            examen = examen,
            matiereId = matiereId,
            nombreQuestions = nombreQuestions
        )
    }

    override suspend fun countQuestions(
        examen: String,
        matiereId: Long
    ): Int {
        return questionDao.countQuestions(
            examen = examen,
            matiereId = matiereId
        )
    }

    override suspend fun getFiches(
        matiereId: Long
    ): List<Fiche> {
        return ficheDao.getFiches(matiereId)
    }

    override suspend fun countFiches(
        matiereId: Long
    ): Int {
        return ficheDao.countFiches(matiereId)
    }

    override suspend fun saveSession(
        session: SessionQuiz
    ): Long {
        return sessionQuizDao.insertSession(session)
    }

    override suspend fun getDernieresSessions(
        limite: Int
    ): List<SessionQuiz> {
        return sessionQuizDao.getDernieresSessions(limite)
    }

    override suspend fun getDerniereSession(): SessionQuiz? {
        return sessionQuizDao.getDerniereSession()
    }

    override suspend fun getSessionsByMatiere(
        matiereId: Long
    ): List<SessionQuiz> {
        return sessionQuizDao.getSessionsByMatiere(matiereId)
    }

    override suspend fun countErreursARevoir(
        examen: String
    ): Int {
        return erreurQuestionDao.countErreurs(examen)
    }


    override suspend fun saveErreur(erreur: ErreurQuestion) =
        erreurQuestionDao.insertErreur(erreur)

    override suspend fun getErreurs(examen: String): List<ErreurQuestion> =
        erreurQuestionDao.getErreurs(examen)

    override suspend fun supprimerErreur(questionId: Long) =
        erreurQuestionDao.supprimerErreur(questionId)


}