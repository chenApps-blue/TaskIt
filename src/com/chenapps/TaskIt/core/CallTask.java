package com.chenapps.TaskIt.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Contacts;

import com.chenapps.TaskIt.core.enums.CallTaskActionKeysType;
import com.chenapps.TaskIt.core.enums.DueDateKeyTypes;
import com.chenapps.TaskIt.core.enums.TaskType;

public class CallTask extends AbstractTask {

	private static final int CONTACT_ID_INDEX = 0;
	private static final int LOOKUP_KEY_INDEX = 1;
	private static final int DISPLAY_NAME_PRIMARY_INDEX = 2;

	private static final String[] PROJECTION = { Contacts._ID,
			Contacts.LOOKUP_KEY, Contacts.DISPLAY_NAME_PRIMARY };

	private static final String SELECTION = Contacts.DISPLAY_NAME_PRIMARY
			+ " LIKE ?";

	private Context context;
	private List<String> contacts;
	private List<String> phoneNumbers;
	private String callToString;
	private long dueDateInMillis;
	private String subject;

	public CallTask(Context context) {
		super(TaskType.Call);
		this.context = context;
		this.contacts = new ArrayList<String>();
		this.phoneNumbers = new ArrayList<String>();
		this.dueDateInMillis = 0;
	}

	@Override
	public boolean ParsText(List<String> text) {
		Dictionary<CallTaskActionKeysType, List<String>> actionsSubstringsDictionary = getTaskSubFieldsStrings(text);
		boolean success = getContactDetails(actionsSubstringsDictionary
				.get(CallTaskActionKeysType.To));
		success = getDueDate(actionsSubstringsDictionary
				.get(CallTaskActionKeysType.DueDate));
		success = getSubject(actionsSubstringsDictionary
				.get(CallTaskActionKeysType.Subject));

		return success;
	}

	private Dictionary<CallTaskActionKeysType, List<String>> getTaskSubFieldsStrings(
			List<String> text) {
		Hashtable<CallTaskActionKeysType, List<String>> rv = new Hashtable<CallTaskActionKeysType, List<String>>();
		List<ISubStringIndices> toActionKeyEndIndices = getActionKeyEndLocation(
				text, ApplicationManager.getInstance().getCallToActionKeys(),
				CallTaskActionKeysType.To);
		List<ISubStringIndices> dueActionKeyEndIndices = getActionKeyEndLocation(
				text, ApplicationManager.getInstance()
						.getCallDueDateActionKeys(),
				CallTaskActionKeysType.DueDate);
		List<ISubStringIndices> subjectActionKeyEndIndices = getActionKeyEndLocation(
				text, ApplicationManager.getInstance()
						.getCallSubjectActionKeys(),
				CallTaskActionKeysType.Subject);

		for (int i = 0; i < toActionKeyEndIndices.size(); i++) {

			List<ISubStringIndices> sortedList = sortAscending(new ArrayList<ISubStringIndices>(
					Arrays.asList(toActionKeyEndIndices.get(i),
							dueActionKeyEndIndices.get(i),
							subjectActionKeyEndIndices.get(i))));

			for (int k = 0; k < sortedList.size(); k++) {
				if (sortedList.get(i).getSubstringEndIndex() + 1 > text.get(i)
						.length())
					continue;
				String substring;
				if (k == sortedList.size() - 1)
					substring = text.get(i).substring(
							sortedList.get(i).getSubstringEndIndex() + 1);
				else
					substring = text.get(i).substring(
							sortedList.get(i).getSubstringEndIndex() + 1,
							sortedList.get(i + 1).getSubstringBeginIndex());
				if (rv.containsKey(sortedList.get(k).getSubstringType()))
					rv.get((CallTaskActionKeysType) sortedList.get(k)
							.getSubstringType()).add(substring);
				else
					rv.put((CallTaskActionKeysType) sortedList.get(k)
							.getSubstringType(),
							new ArrayList<String>(Arrays.asList(substring)));
			}

		}

		return rv;
	}

	private List<ISubStringIndices> sortAscending(
			List<ISubStringIndices> unSortedArray) {

		boolean replaceOccurred = true;
		while (replaceOccurred) {
			replaceOccurred = false;
			for (int i = 0; i < unSortedArray.size() - 1; i++) {
				if (unSortedArray.get(i).getSubstringBeginIndex() > unSortedArray
						.get(i + 1).getSubstringBeginIndex()) {
					ISubStringIndices switchTempVar = unSortedArray.get(i);
					unSortedArray.set(i, unSortedArray.get(i + 1));
					unSortedArray.set(i + 1, switchTempVar);
					replaceOccurred = true;
				}
			}
		}
		return unSortedArray;
	}

	private List<ISubStringIndices> getActionKeyEndLocation(
			List<String> textList, List<String> callToActionKeys,
			CallTaskActionKeysType callTaskActionKeyType) {
		List<ISubStringIndices> rv = new ArrayList<ISubStringIndices>();
		for (String text : textList) {
			int substringBeginIndex = -1;
			int substringEndIndex = -1;
			for (String actionKey : callToActionKeys) {
				int subStringLocation = text.indexOf(actionKey);
				if (subStringLocation != -1) {
					substringBeginIndex = subStringLocation;
					substringEndIndex = subStringLocation + actionKey.length();
					break;
				}
			}
			rv.add(new SubStringIndices(callTaskActionKeyType,
					substringBeginIndex, substringEndIndex));
		}
		return rv;
	}

	private boolean getSubject(List<String> subjectStrings) {
		subject = subjectStrings.get(0);
		return true;
	}

	private boolean getDueDate(List<String> dateStrings) {
		for (String dateString : dateStrings) {
			getDate(dateString);
			getHour(dateString);
		}
		return true;
	}

	private void getHour(String dateString) {
		// TODO Auto-generated method stub
 	}

	private void getDate(String dateString) {

		List<IDueDateKey> dateKeys = new ArrayList<IDueDateKey>();
		for (IDueDateKey dateKey : dateKeys) {
			if (dateString.contains(dateKey.getKey())) {
				getDate(dateKey);
				break;
			}
		}
	}

	private void getDate(IDueDateKey dateKey) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		long currentDayInMillis = calendar.getTimeInMillis();
		if (dateKey.getDateType() == DueDateKeyTypes.Relative) {
			calendar.set(calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			dueDateInMillis = calendar.getTimeInMillis()
					+ dateKey.getDateOffsetInMillis();
		}
		if (dateKey.getDateType() == DueDateKeyTypes.Absolute)
			dueDateInMillis = dateKey.getDateInMillis();
	}

	private boolean getContactDetails(List<String> callTostrings) {

		for (String text : callTostrings) {
			String[] words = text.split(" ");
			for (String word : words) {
				List<String> contacNames = searchInContacts(word);
				if (contacNames != null) {
					for (String contactName : contacNames) {
						if (contacts.contains(contactName))
							contacts.add(contactName);
					}
				}
				if (checkIfPhoneNumber(word)) {
					if (!phoneNumbers.contains(word))
						phoneNumbers.add(word);
				}
			}
		}
		callToString = callTostrings.get(0);
		return true;
	}

	private boolean checkIfPhoneNumber(String phoneString) {
		return phoneString.matches(ApplicationManager.getInstance()
				.getPhoneNumberRegexpString());
	}

	private List<String> searchInContacts(String searchString) {

		List<String> rv = new ArrayList<String>();
		String[] mSelectionArgs = { "%" + searchString + "%" };
		Cursor contacts = context
				.getApplicationContext()
				.getContentResolver()
				.query(Contacts.CONTENT_URI, PROJECTION, SELECTION,
						mSelectionArgs, null);

		while (contacts.moveToNext()) {
			String contactName = contacts.getString(DISPLAY_NAME_PRIMARY_INDEX);
			// contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY));
			rv.add(contactName);
		}
		return rv;
	}

}