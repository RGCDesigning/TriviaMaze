from typing import Union


class Question:

    def __init__(self, question_type: str, question: str, answer: Union[str, int], choices=None):
        if choices is None:
            choices = []
        self.question_type = question_type
        self.question = question
        self.answer = answer
        self.choices = choices

    def __str__(self) -> str:
        return f"{self.question_type} : {self.question} : {self.answer} : {self.choices}"