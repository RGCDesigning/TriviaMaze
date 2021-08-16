import sqlite3
import pickle
import typing
import ScrapeOpenSourceQ as sosq

from Question import Question


def write_questions_to_db(db_path: str, questions: typing.List[Question]):
    con = sqlite3.connect(db_path)

    cur = con.cursor()

    for q in questions:
        cur.execute(f"INSERT INTO Questions VALUES ('{q.question_type}', '{q.question}', '{'~'.join(q.choices)}', '{q.answer}')")

    con.commit()

    con.close()


def write_to_db(db_path: str, question_file_path: str):
    con = sqlite3.connect(db_path)

    cur = con.cursor()

    questions = pickle.load(open(question_file_path, "rb"))

    for q in questions:
        cur.execute(f"INSERT INTO Questions VALUES ('{q.question_type}', '{q.question}', '{'~'.join(q.choices)}', '{q.answer}')")

    con.commit()

    con.close()


write_questions_to_db('Test.db', sosq.get_questions(50, 14, 'multiple', None))