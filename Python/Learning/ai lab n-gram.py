import nltk
from nltk import trigrams
from nltk.corpus import reuters
from collections import defaultdict

nltk.download('reuters')
nltk.download('punkt')
nltk.download('punkt_tab')

words = nltk.word_tokenize(' '.join(reuters.words()))
tri_grams = list(trigrams(words)) # makes the words into a tri

model = defaultdict(lambda: defaultdict(lambda: 0))

for w1, w2, w3 in tri_grams:
    model[(w1, w2)][w3] += 1 # just counts

for w1_w2 in model:
    total_count = float(sum(model[w1_w2].values()))
    
    for w3 in model[w1_w2]:
        model[w1_w2][w3] /= total_count


def predict_next_word(w1, w2): # uses probability to predict the next words
    next_word_probs = model[(w1, w2)]
    
    if next_word_probs:
        return max(next_word_probs, key=next_word_probs.get)
    else:
        return "No prediction available"


print("Next Word:", predict_next_word('the', 'stock'))

