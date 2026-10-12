import nltk
from nltk import bigrams
from nltk.corpus import reuters
from collections import defaultdict

nltk.download('reuters')
nltk.download('punkt')
nltk.download('punkt_tab')

words = nltk.word_tokenize(' '.join(reuters.words()))
bi_grams = list(bigrams(words)) # makes the words into a bi

model = defaultdict(lambda: defaultdict(lambda: 0))

for w1, w2 in bi_grams:
    model[w1][w2] += 1 # just counts

for w1 in model:
    total_count = float(sum(model[w1].values()))
    for w2 in model[w1]:
        model[w1][w2] /= total_count
    
    
        


def predict_next_word(w1): # uses probability to predict the next words
    next_word_probs = model[w1]
    
    if next_word_probs:
        return max(next_word_probs, key=next_word_probs.get)
    else:
        return "No prediction available"


print("Next Word:", predict_next_word('the'))

