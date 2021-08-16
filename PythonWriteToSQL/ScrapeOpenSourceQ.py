import requests
import random
import pickle

from Question import Question


'''

'''
def get_questions(amount: int, category: int, question_type: str, save_location: str):
    url = f'https://opentdb.com/api.php?amount={amount}&category={category}&type={question_type}'

    resp = requests.get(url)
    data = resp.json()

    questions = []

    for q in data['results']:
        question_type = 0
        question = q['question']
        answer = q['correct_answer']
        choices = q['incorrect_answers']

        rand_insert = random.randrange(len(choices) + 1)

        choices.insert(rand_insert, answer)

        temp_question = Question(str(question_type), question, answer=rand_insert, choices=choices)

        questions.append(temp_question)

    if save_location is not None:
        pickle.dump(questions, open(save_location, "wb"))

    return questions


questions = get_questions(50, 14, 'multiple', None)

for q in questions:
    print(q)
