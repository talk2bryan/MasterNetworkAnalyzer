import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
import sklearn
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split


train = pd.read_csv('sheet3.csv')

print(train.head())


sns.heatmap(train.isnull(),yticklabels=False,cbar=False,cmap='viridis')

plt.show()


sns.set_style('whitegrid')
sns.countplot(x='Fog faster than Cloud ?',data=train,palette='RdBu_r')

plt.show()


train.dropna(inplace=True)
train.head()

plt.show()

train.info()

columns = 'm n'.split() # Declare the columns names

train['Fog faster than Cloud ?'] = train['Fog faster than Cloud ?'].astype(int)

df = pd.DataFrame(train, columns=columns)

X_train, X_test, y_train, y_test = train_test_split(df,train['Fog faster than Cloud ?'], test_size=0.30,random_state=101)

print(y_train)

logmodel = LogisticRegression()
logmodel.fit(X_train,y_train)
predictions = logmodel.predict(X_test)


from sklearn.metrics import classification_report
print(classification_report(y_test,predictions))
print("Accuracy:",sklearn.metrics.accuracy_score(y_test, predictions))
