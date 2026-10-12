import nltk

from nltk.sentiment.vader import SentimentIntensityAnalyzer



nltk.download('vader_lexicon')


analyser = SentimentIntensityAnalyzer() # creates a sentiment analyser


text = input("Enter a text: ")

print(analyser.polarity_scores(text))

from nltk.sentiment.vader import SentimentIntensityAnalyzer 
analyser = SentimentIntensityAnalyzer() 
text = input ("Enter a text:" ) 
print(analyser.polarity_scores(text))


# this asks you to type a tri compound and itll print out how negative neutral and positive it thinks the sentence is
