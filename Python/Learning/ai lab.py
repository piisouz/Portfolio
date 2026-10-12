import re # task 2
import inflect # task 3
import string # task 4
import nltk # task 6 , task 8, task 9
from nltk.corpus import stopwords# task 6
from nltk.tokenize import word_tokenize# task 6 , task 7, task 8 , task 9
from nltk.stem import PorterStemmer # task 7
from nltk.stem import WordNetLemmatizer # task 8
from nltk import pos_tag #task 9


 #convert text to lowercase task 1

s1 = "Hey, did you know that the summer break is coming? Amazing right !! It's only 5 more days !!"
print(s1.lower())



# removes numbers from text task 2

s2 = "There are 3 balls in this bag, and 12 in the other one."
print(re.sub(r'\d+', '', s2)) 


# tasks 3 converting numbers to words

p = inflect.engine()


s3 = "There are 3 balls in this bag, and 12 in the other one."
temp = s3.split()
s4 = []

for word in temp:
    if word.isdigit():
        s4.append(p.number_to_words(word))
    else:
        s4.append(word)
res = ' '.join(s4)
print(res)


#task 4 remove punctuation

s5 = "Hey, did you know that the summer break is coming? Amazing right !! It's only 5 more days !!"

trans = str.maketrans('', '', string.punctuation)
s6 = s5.translate(trans)
print(s6)

#task 5 remove white spaces extra ones

s7 = "we don't need   the given   questions"
s8 = " ".join(s7.split())
print(s8)

#task 6 removing stopwords(words that dont add much meaning to a sentence)

nltk.download('punkt')
nltk.download('stopwords')

text = "This is a sample sentence and we will remove stopwords."
tokens = word_tokenize(text)
f1 = [w for w in tokens if w.lower() not in stopwords.words('english')]
print(f1)


#task 7 stemming which is the process of getting to the root of the word for example looked = look

s9 = PorterStemmer()
text = "data science uses scientific methods algorithms and many types of processes"

w1 = word_tokenize(text)
stems = [s9.stem(word) for word in w1]

print(stems)


#task 8 lemmatiazation improved versio of stemming

nltk.download('wordnet')

le = WordNetLemmatizer()
text = "data science uses scientific methods algorithms"

words = word_tokenize(text)
lemmas = [le.lemmatize(w) for w in words]
print(lemmas)

#task 9 tagging which is giving words there grammatical role

nltk.download('averaged_perceptron_tagger')

text = "Data science combines statistics, programming, and machine learning."
tokens = word_tokenize(text)
tags = pos_tag(tokens)
print(tags)
